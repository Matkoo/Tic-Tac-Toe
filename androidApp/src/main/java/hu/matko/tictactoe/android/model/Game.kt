package hu.matko.tictactoe.android.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * @author Matkovics Gergely<br></br>
 * E-mail: [gergelymatkovics82@gmail.com](mailto:gergelymatkovics82@gmail.com)
 */

object Game : IntIdTable("game_data"){

    var gameDate = varchar("game_date",255)
    var winner = varchar("winner",255)
    var gamePos = varchar("game_position",255)

}

class GameEntity (id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<GameEntity>(Game)
    var gameDate: String? = null
    var winner: String? = null
    var gamePos: String? = null

}