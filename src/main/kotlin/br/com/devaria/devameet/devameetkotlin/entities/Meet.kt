package br.com.devaria.devameet.devameetkotlin.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
data class Meet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long = 0,
    val name: String = "",
    val link: String = "",
    val color: String = "",

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    val user: User? = null,

    @JsonBackReference
    @OneToMany(mappedBy = "meet")
    val objects: List<MeetObject> = emptyList()
)