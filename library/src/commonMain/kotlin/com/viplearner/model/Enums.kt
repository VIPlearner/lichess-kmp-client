package com.viplearner.model

interface Enums {
    enum class ColorPref {
        random,
        white,
        black,
    }

    enum class Color {
        white,
        black,
        ;

        interface Provider {
            fun white(): Color {
                return com.viplearner.model.Enums.Color.white
            }

            fun black(): Color {
                return com.viplearner.model.Enums.Color.black
            }
        }
    }

    enum class Outcome {
        win,
        draw,
        loss,
        none,
    }

    enum class Status(val status: Int) {
        created(10),
        started(20),
        aborted(25),
        mate(30),
        resign(31),
        stalemate(32),
        timeout(33),
        draw(34),
        outoftime(35),
        cheat(36),
        noStart(37),
        unknownFinish(38),
        insufficientMaterialClaim(39),
        variantEnd(60),
        ;

        fun status(): Int {
            return status
        }

        companion object {
            fun valueOf(status: Int): Status {
                return when (status) {
                    10 -> com.viplearner.model.Enums.Status.created
                    20 -> com.viplearner.model.Enums.Status.started
                    25 -> com.viplearner.model.Enums.Status.aborted
                    30 -> com.viplearner.model.Enums.Status.mate
                    31 -> com.viplearner.model.Enums.Status.resign
                    32 -> com.viplearner.model.Enums.Status.stalemate
                    33 -> com.viplearner.model.Enums.Status.timeout
                    34 -> com.viplearner.model.Enums.Status.draw
                    35 -> com.viplearner.model.Enums.Status.outoftime
                    36 -> com.viplearner.model.Enums.Status.cheat
                    37 -> com.viplearner.model.Enums.Status.noStart
                    38 -> com.viplearner.model.Enums.Status.unknownFinish
                    39 -> com.viplearner.model.Enums.Status.insufficientMaterialClaim
                    60 -> com.viplearner.model.Enums.Status.variantEnd
                    else -> throw IllegalArgumentException("Unknown game status: " + status)
                }
            }
        }
    }

    enum class Direction {
        `in`,
        out,
    }

    enum class Speed {
        ultraBullet,
        bullet,
        blitz,
        rapid,
        classical,
        correspondence,
        ;

        interface Provider {
            fun ultraBullet(): Speed {
                return Speed.ultraBullet
            }

            fun bullet(): Speed {
                return Speed.bullet
            }

            fun blitz(): Speed {
                return Speed.blitz
            }

            fun rapid(): Speed {
                return Speed.rapid
            }

            fun classical(): Speed {
                return Speed.classical
            }

            fun correspondence(): Speed {
                return Speed.correspondence
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {}
            }
        }
    }

    /**
     * Specifies a rating group, which includes ratings up to next rating
     * group.<br></br>
     * _1600 indicates ratings between 1600-1800 and
     * _2500 indicates ratings from 2500 and up.
     */
    enum class RatingGroup {
        _0,
        _1000,
        _1200,
        _1400,
        _1600,
        _1800,
        _2000,
        _2200,
        _2500,
        ;

        fun asString(): String {
            return name.substring(1)
        }

        interface Provider {
            /**
             * 600-1000
             */
            fun _0(): RatingGroup {
                return com.viplearner.model.Enums.RatingGroup._0
            }

            /**
             * 1000-1200
             */
            fun _1000(): RatingGroup {
                return com.viplearner.model.Enums.RatingGroup._1000
            }

            /**
             * 1200-1400
             */
            fun _1200(): RatingGroup {
                return com.viplearner.model.Enums.RatingGroup._1200
            }

            /**
             * 1400-1600
             */
            fun _1400(): RatingGroup {
                return com.viplearner.model.Enums.RatingGroup._1400
            }

            /**
             * 1600-1800
             */
            fun _1600(): RatingGroup {
                return com.viplearner.model.Enums.RatingGroup._1600
            }

            /**
             * 1800-2000
             */
            fun _1800(): RatingGroup {
                return com.viplearner.model.Enums.RatingGroup._1800
            }

            /**
             * 2000-2200
             */
            fun _2000(): RatingGroup {
                return com.viplearner.model.Enums.RatingGroup._2000
            }

            /**
             * 2200-2500
             */
            fun _2200(): RatingGroup {
                return com.viplearner.model.Enums.RatingGroup._2200
            }

            /**
             * 2500-over 9000!
             */
            fun _2500(): RatingGroup {
                return com.viplearner.model.Enums.RatingGroup._2500
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {
                }
            }
        }
    }

    enum class VariantName {
        standard,
        chess960,
        crazyhouse,
        antichess,
        atomic,
        horde,
        kingOfTheHill,
        racingKings,
        threeCheck,
        ;

        interface Provider {
            fun standard(): VariantName {
                return com.viplearner.model.Enums.VariantName.standard
            }

            fun chess960(): VariantName {
                return com.viplearner.model.Enums.VariantName.chess960
            }

            fun crazyhouse(): VariantName {
                return com.viplearner.model.Enums.VariantName.crazyhouse
            }

            fun antichess(): VariantName {
                return com.viplearner.model.Enums.VariantName.antichess
            }

            fun atomic(): VariantName {
                return com.viplearner.model.Enums.VariantName.atomic
            }

            fun horde(): VariantName {
                return com.viplearner.model.Enums.VariantName.horde
            }

            fun kingOfTheHill(): VariantName {
                return com.viplearner.model.Enums.VariantName.kingOfTheHill
            }

            fun racingKings(): VariantName {
                return com.viplearner.model.Enums.VariantName.racingKings
            }

            fun threeCheck(): VariantName {
                return com.viplearner.model.Enums.VariantName.threeCheck
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {}
            }
        }
    }

    enum class GameVariant {
        standard,
        chess960,
        crazyhouse,
        antichess,
        atomic,
        horde,
        kingOfTheHill,
        racingKings,
        threeCheck,
        fromPosition,
        ;

        interface Provider {
            fun standard(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.standard
            }

            fun chess960(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.chess960
            }

            fun crazyhouse(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.crazyhouse
            }

            fun antichess(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.antichess
            }

            fun atomic(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.atomic
            }

            fun horde(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.horde
            }

            fun kingOfTheHill(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.kingOfTheHill
            }

            fun racingKings(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.racingKings
            }

            fun threeCheck(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.threeCheck
            }

            fun fromPosition(): GameVariant {
                return com.viplearner.model.Enums.GameVariant.fromPosition
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {}
            }
        }
    }

    /**
     * Specifies who will be able to chat
     */
    enum class ChatFor(val id: Int) {
        none(0),
        onlyTeamLeaders(10),
        onlyTeamMembers(20),
        allLichessPlayers(30),
        ;

        interface Provider {
            fun none(): ChatFor {
                return com.viplearner.model.Enums.ChatFor.none
            }

            fun onlyTeamLeaders(): ChatFor {
                return com.viplearner.model.Enums.ChatFor.onlyTeamLeaders
            }

            fun onlyTeamMembers(): ChatFor {
                return com.viplearner.model.Enums.ChatFor.onlyTeamMembers
            }

            fun allLichessPlayers(): ChatFor {
                return com.viplearner.model.Enums.ChatFor.allLichessPlayers
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {}
            }
        }
    }

    /**
     * The reasons for declining a challenge
     */
    enum class DeclineReason {
        generic,
        later,
        tooFast,
        tooSlow,
        timeControl,
        rated,
        casual,
        standard,
        variant,
        noBot,
        onlyBot,
        ;

        interface Provider {
            fun generic(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.generic
            }

            fun later(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.later
            }

            fun tooFast(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.tooFast
            }

            fun tooSlow(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.tooSlow
            }

            fun timeControl(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.timeControl
            }

            fun rated(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.rated
            }

            fun casual(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.casual
            }

            fun standard(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.standard
            }

            fun variant(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.variant
            }

            fun noBot(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.noBot
            }

            fun onlyBot(): DeclineReason {
                return com.viplearner.model.Enums.DeclineReason.onlyBot
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {}
            }
        }
    }

    /**
     * The TV Channels
     */
    enum class Channel {
        bot,
        blitz,
        racingKings,
        ultraBullet,
        bullet,
        classical,
        threeCheck,
        antichess,
        computer,
        horde,
        rapid,
        atomic,
        crazyhouse,
        chess960,
        kingOfTheHill,
        topRated,
        ;

        interface Provider {
            fun bot(): Channel {
                return com.viplearner.model.Enums.Channel.bot
            }

            fun blitz(): Channel {
                return com.viplearner.model.Enums.Channel.blitz
            }

            fun racingKings(): Channel {
                return com.viplearner.model.Enums.Channel.racingKings
            }

            fun ultraBullet(): Channel {
                return com.viplearner.model.Enums.Channel.ultraBullet
            }

            fun bullet(): Channel {
                return com.viplearner.model.Enums.Channel.bullet
            }

            fun classical(): Channel {
                return com.viplearner.model.Enums.Channel.classical
            }

            fun threeCheck(): Channel {
                return com.viplearner.model.Enums.Channel.threeCheck
            }

            fun antichess(): Channel {
                return com.viplearner.model.Enums.Channel.antichess
            }

            fun computer(): Channel {
                return com.viplearner.model.Enums.Channel.computer
            }

            fun horde(): Channel {
                return com.viplearner.model.Enums.Channel.horde
            }

            fun rapid(): Channel {
                return com.viplearner.model.Enums.Channel.rapid
            }

            fun atomic(): Channel {
                return com.viplearner.model.Enums.Channel.atomic
            }

            fun crazyhouse(): Channel {
                return com.viplearner.model.Enums.Channel.crazyhouse
            }

            fun chess960(): Channel {
                return com.viplearner.model.Enums.Channel.chess960
            }

            fun kingOfTheHill(): Channel {
                return com.viplearner.model.Enums.Channel.kingOfTheHill
            }

            fun topRated(): Channel {
                return com.viplearner.model.Enums.Channel.topRated
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {}
            }
        }
    }

    /**
     * The possible performance types
     */
    enum class PerfType {
        antichess,
        atomic,
        blitz,
        bullet,
        chess960,
        classical,
        correspondence,
        crazyhouse,
        horde,
        kingOfTheHill,
        racingKings,
        rapid,
        threeCheck,
        ultraBullet,
        ;

        interface Provider {
            fun atomic(): PerfType {
                return com.viplearner.model.Enums.PerfType.atomic
            }

            fun antichess(): PerfType {
                return com.viplearner.model.Enums.PerfType.antichess
            }

            fun blitz(): PerfType {
                return com.viplearner.model.Enums.PerfType.blitz
            }

            fun bullet(): PerfType {
                return com.viplearner.model.Enums.PerfType.bullet
            }

            fun chess960(): PerfType {
                return com.viplearner.model.Enums.PerfType.chess960
            }

            fun classical(): PerfType {
                return com.viplearner.model.Enums.PerfType.classical
            }

            fun correspondence(): PerfType {
                return com.viplearner.model.Enums.PerfType.correspondence
            }

            fun crazyhouse(): PerfType {
                return com.viplearner.model.Enums.PerfType.crazyhouse
            }

            fun horde(): PerfType {
                return com.viplearner.model.Enums.PerfType.horde
            }

            fun kingOfTheHill(): PerfType {
                return com.viplearner.model.Enums.PerfType.kingOfTheHill
            }

            fun racingKings(): PerfType {
                return com.viplearner.model.Enums.PerfType.racingKings
            }

            fun rapid(): PerfType {
                return com.viplearner.model.Enums.PerfType.rapid
            }

            fun threeCheck(): PerfType {
                return com.viplearner.model.Enums.PerfType.threeCheck
            }

            fun ultraBullet(): PerfType {
                return com.viplearner.model.Enums.PerfType.ultraBullet
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {}
            }
        }
    }

    /**
     * The possible performance types, excluding correspondence
     */
    enum class PerfTypeNoCorr {
        antichess,
        atomic,
        blitz,
        bullet,
        chess960,
        classical,
        crazyhouse,
        horde,
        kingOfTheHill,
        racingKings,
        rapid,
        threeCheck,
        ultraBullet,
        ;

        interface Provider {
            fun atomic(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.atomic
            }

            fun antichess(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.antichess
            }

            fun blitz(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.blitz
            }

            fun bullet(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.bullet
            }

            fun chess960(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.chess960
            }

            fun classical(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.classical
            }

            fun crazyhouse(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.crazyhouse
            }

            fun horde(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.horde
            }

            fun kingOfTheHill(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.kingOfTheHill
            }

            fun racingKings(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.racingKings
            }

            fun rapid(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.rapid
            }

            fun threeCheck(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.threeCheck
            }

            fun ultraBullet(): PerfTypeNoCorr {
                return com.viplearner.model.Enums.PerfTypeNoCorr.ultraBullet
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {}
            }
        }
    }

    enum class PerfTypeWithFromPos {
        antichess,
        atomic,
        blitz,
        bullet,
        chess960,
        classical,
        correspondence,
        crazyhouse,
        horde,
        kingOfTheHill,
        racingKings,
        rapid,
        threeCheck,
        ultraBullet,
        fromPosition,
        ;

        interface Provider {
            fun atomic(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.atomic
            }

            fun antichess(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.antichess
            }

            fun blitz(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.blitz
            }

            fun bullet(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.bullet
            }

            fun chess960(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.chess960
            }

            fun classical(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.classical
            }

            fun correspondence(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.correspondence
            }

            fun crazyhouse(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.crazyhouse
            }

            fun horde(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.horde
            }

            fun kingOfTheHill(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.kingOfTheHill
            }

            fun racingKings(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.racingKings
            }

            fun rapid(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.rapid
            }

            fun threeCheck(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.threeCheck
            }

            fun ultraBullet(): PerfTypeWithFromPos {
                return com.viplearner.model.Enums.PerfTypeWithFromPos.ultraBullet
            }
        }

        companion object {
            fun provider(): Provider {
                return object : Provider {}
            }
        }
    }
}
