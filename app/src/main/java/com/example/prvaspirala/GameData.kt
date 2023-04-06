package com.example.prvaspirala

class GameData {
    companion object Games {
        fun getAll(): List<Game> {
            return listOf(
                Game(
                    "FIFA 23",
                    "PlayStation",
                    "August 22, 2022",
                    8.1,
                    "fifa23",
                    "E",
                    "EA Sports",
                    "EA",
                    "Sports",
                    "FIFA 23 features the men's World Cup game mode and is reported to feature the women's World Cup game mode in the future, replicating the 2022 FIFA World Cup and the 2023 FIFA Women's World Cup.",
                    mutableListOf(
                        UserReview("bot1", 1, "The new HyperMotion technology in FIFA 23 is a game-changer, making the gameplay smoother, more fluid and immersive than ever before."),
                        UserReview("bot2", 2, "While the graphics and visuals in FIFA 23 are impressive, some player faces and animations still look unrealistic and awkward, which can be distracting during gameplay."),
                        UserReview("bot3", 3, "The updated Career Mode offers more customization options, with the Create a Club feature, and revamped player development system, making the game more engaging for players."),
                        UserReview("bot4", 4, "The online matchmaking system still needs improvement, with players often being matched with opponents of vastly different skill levels, leading to unfair and frustrating matches."),
                        UserReview("bot5", 5, "The presentation of FIFA 23 is top-notch, with impressive visuals, realistic player animations, and accurate stadium details, making the overall experience feel authentic."),
                    )
                ),
                Game(
                    "Wild Hunt", "PlayStation 4/Xbox One/PC", "May 19, 2015", 9.3,
                    "witcher", "M", "CD Projekt Red", "CD Projekt", "Action-RPG",
                    "The game follows Geralt of Rivia, a monster hunter who sets out on a quest to find his adopted daughter and confront the Wild Hunt, a group of spectral riders.",
                    mutableListOf(
                        UserRating("bot1", 1, 5.0),
                        UserRating("bot2", 2, 4.0),
                        UserRating("bot3", 3, 3.5),
                        UserRating("bot4", 4, 5.0),
                        UserRating("bot5", 5, 4.1)
                    )
                ),
                Game(
                    "Modern Warfare 2",
                    "PlayStation 3/Xbox 360/PC",
                    "November 10, 2009",
                    9.5,
                    "cod",
                    "M",
                    "Infinity Ward",
                    "Activision",
                    "First-person shooter",
                    "Call of Duty: Modern Warfare 2 is a first-person shooter that follows the story of a special forces operative as he attempts to stop a Russian ultranationalist from taking over the world.",
                    mutableListOf()
                ),
                Game(
                    "Fortnite",
                    "PlayStation/PC/Nintendo Switch/Xbox",
                    "July 25, 2017",
                    8.3,
                    "fortnite",
                    "T",
                    "Epic Games",
                    "Epic Games",
                    "Battle royale, sandbox",
                    "Fortnite is a popular online multiplayer game that pits players against each other in a last-man-standing battle royale. Players must scavenge for weapons and resources while avoiding being eliminated by other players. The game also features a sandbox mode where players can build structures and explore the map. Fortnite has gained a massive following and has become a cultural phenomenon, with numerous celebrity fans and collaborations.",
                    mutableListOf()
                ),
                Game(
                    "Ocarina of Time",
                    "Nintendo 64/Nintendo GameCube/Nintendo 3DS",
                    "November 23, 1998",
                    10.0,
                    "lzot",
                    "E",
                    "Nintendo EAD",
                    "Nintendo",
                    "Action-adventure",
                    "The Legend of Zelda: Ocarina of Time is a classic action-adventure video game developed and published by Nintendo for the Nintendo 64 console in 1998. It is the fifth installment in the Legend of Zelda series and is widely regarded as one of the greatest video games of all time.",
                    mutableListOf()
                ),
                Game(
                    "GTA V",
                    "PlayStation 4/Xbox One/Xbox 360/PC",
                    "September 17, 2013",
                    9.3,
                    "gta5",
                    "M",
                    "Rockstar North",
                    "Rockstar Games",
                    "Action-adventure",
                    "In Grand Theft Auto V, players take on the roles of three criminals attempting to pull off a series of heists while being pursued by the police and other criminal organizations.",
                    mutableListOf()
                ),
                Game(
                    "Breath of the Wild",
                    "Nintendo Switch/Wii U",
                    "March 3, 2017",
                    9.5,
                    "lfbw",
                    "E10+",
                    "Nintendo EPD",
                    "Nintendo",
                    "Action-adventure",
                    "The Legend of Zelda: Breath of the Wild is an action-adventure game set in an open world environment.",
                    mutableListOf()
                ),
                Game(
                    "Super Mario Bros",
                    "NES",
                    "September 13, 1985",
                    9.0,
                    "mario",
                    "E",
                    "Nintendo EAD",
                    "Nintendo",
                    "Platformer",
                    "The game follows Mario, a plumber who sets out to rescue Princess Toadstool from the evil Bowser.",
                    mutableListOf()
                ),
                Game(
                    "Half-Life 2",
                    "PC",
                    "November 16, 2004",
                    9.4,
                    "half2",
                    "M",
                    "Valve Corporation",
                    "Valve Corporation",
                    "First-person shooter",
                    "The game follows Gordon Freeman as he battles an alien race known as the Combine and helps a resistance movement in a dystopian future.",
                    mutableListOf()
                ),
                Game(
                    "Red Dead 2",
                    "PlayStation 4/Xbox One/PC",
                    "October 26, 2018",
                    9.8,
                    "red2",
                    "M",
                    "Rockstar Studios",
                    "Rockstar Games",
                    "Action-Adventure",
                    "The game follows Arthur Morgan, a member of a gang of outlaws in the dying days of the Wild West.",
                    mutableListOf()
                )
            )
        }

        fun getDetails(title: String): Game? {
            return Games.getAll().find { it.title == title }
        }
    }
}