package br.com.devaria.devameet.devameetkotlin.services

import br.com.devaria.devameet.devameetkotlin.dtos.room.JoinRoomRequestDto
import br.com.devaria.devameet.devameetkotlin.dtos.room.JoinRoomSocketResponseDto
import br.com.devaria.devameet.devameetkotlin.dtos.room.UpdatePositionRequestDto
import br.com.devaria.devameet.devameetkotlin.dtos.room.UpdateUsersSocketResponseDto
import br.com.devaria.devameet.devameetkotlin.entities.Position
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.listener.DisconnectListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SocketService(
    server: SocketIOServer,
    private val roomService: RoomService
){
    private val server : SocketIOServer

    private val log =  LoggerFactory.getLogger(SocketService::class.java)

    init{
        this.server = server
        this.server.addConnectListener(onConnected())
        this.server.addDisconnectListener(onDisconnected())
        this.server.addEventListener("join", JoinRoomRequestDto::class.java, onJoin())
    }

    private fun onJoin(): DataListener<JoinRoomRequestDto>? {
        return DataListener { client, data, ackSender ->
            val position = roomService.findByClientId(client.sessionId.toString())

            var result: Position? = null

            if(position == null){
                data.clientId = client.sessionId.toString()

                val dto = UpdatePositionRequestDto(
                    userId = data.userId,
                    clientId = client.sessionId.toString(),
                    link = data.link
                )

                result = roomService.updateUserPosition(dto)

                if(result != null){
                    client.joinRoom(data.link)
                    sendToOthersClient("${data.link}-add-user", client, JoinRoomSocketResponseDto(client.sessionId.toString()))
                }
            }

            val users = roomService.listPosition(data.link)
            sendToAllClients("${data.link}-update-user-list", client, UpdateUsersSocketResponseDto(users))
            log.info("Socket client: ${client.sessionId} started to join room ${data.link}")
        }
    }

    private fun onDisconnected(): DisconnectListener? {
        return DisconnectListener { client -> log.info("ClientId ${client.sessionId} disconnected to socket") }
    }

    private fun onConnected(): ConnectListener? {
        return ConnectListener { client -> log.info("ClientId ${client.sessionId} connected to socket") }
    }

    private fun sendToAllClients(eventName: String, senderClient: SocketIOClient, data: Any) {
        for(client in senderClient.namespace.getRoomOperations("").clients){
            client.sendEvent(eventName, data)
        }
    }

    private fun sendToOthersClient(eventName: String, senderClient: SocketIOClient, data: Any) {
        for(client in senderClient.namespace.getRoomOperations("").clients){
            if(client.sessionId != senderClient.sessionId){
                client.sendEvent(eventName, data)
            }
        }
    }
}