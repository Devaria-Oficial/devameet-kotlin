package br.com.devaria.devameet.devameetkotlin.services

import br.com.devaria.devameet.devameetkotlin.dtos.meet.MeetObjectRequestDto
import br.com.devaria.devameet.devameetkotlin.dtos.room.GetRoomResponseDto
import br.com.devaria.devameet.devameetkotlin.exceptions.BadRequestException
import br.com.devaria.devameet.devameetkotlin.repositories.MeetObjectRepository
import br.com.devaria.devameet.devameetkotlin.repositories.MeetRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class RoomService(
    private val meetRepository: MeetRepository,
    private val meetObjectRepository: MeetObjectRepository
) {

    private val log = LoggerFactory.getLogger(RoomService::class.java)

    @Throws(BadRequestException::class)
    fun getRoom(link: String) : GetRoomResponseDto{
        log.info("getRoom - start")

        val meet = meetRepository.findByLink(link)
        if(meet == null || meet.id <= 0){
            throw BadRequestException(mutableListOf("Reunião não encontrada"))
        }

        val meetObjects = meetObjectRepository.findAllByMeetId(meet.id)
        val objects = meetObjects.map { MeetObjectRequestDto(
            name = it.name,
            x = it.x,
            y = it.y,
            orientation = it.orientation,
            zIndex =  it.zIndex
        )}

        val result = GetRoomResponseDto(
            name = meet.name,
            color = meet.color,
            link = meet.link,
            objects
        )

        log.info("getRoom - success")
        return result
    }
}