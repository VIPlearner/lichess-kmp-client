package com.viplearner.api.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
enum class TimelineEntriesItemsTimelineEntryFollowType {
    @SerialName("follow")
    FOLLOW
}

@Serializable
data class TimelineEntriesItemsTimelineEntryFollowData(
    val u1: String,
    val u2: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryTeamJoinType {
    @SerialName("team-join")
    TEAM_JOIN
}

@Serializable
data class TimelineEntriesItemsTimelineEntryTeamJoinData(
    val userId: String,
    val teamId: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryTeamCreateType {
    @SerialName("team-create")
    TEAM_CREATE
}

@Serializable
data class TimelineEntriesItemsTimelineEntryTeamCreateData(
    val userId: String,
    val teamId: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryForumPostType {
    @SerialName("forum-post")
    FORUM_POST
}

@Serializable
data class TimelineEntriesItemsTimelineEntryForumPostData(
    val userId: String,
    val topicId: String,
    val topicName: String,
    val postId: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryBlogPostType {
    @SerialName("blog-post")
    BLOG_POST
}

@Serializable
data class TimelineEntriesItemsTimelineEntryBlogPostData(
    val id: String,
    val slug: String,
    val title: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryUblogPostType {
    @SerialName("ublog-post")
    UBLOG_POST
}

@Serializable
data class TimelineEntriesItemsTimelineEntryUblogPostData(
    val userId: String,
    val id: String,
    val slug: String,
    val title: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryTourJoinType {
    @SerialName("tour-join")
    TOUR_JOIN
}

@Serializable
data class TimelineEntriesItemsTimelineEntryTourJoinData(
    val userId: String,
    val tourId: String,
    val tourName: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryGameEndType {
    @SerialName("game-end")
    GAME_END
}

@Serializable
data class TimelineEntriesItemsTimelineEntryGameEndData(
    val fullId: String,
    val opponent: String,
    val win: Boolean,
    val perf: PerfType
)

@Serializable
enum class TimelineEntriesItemsTimelineEntrySimulType {
    @SerialName("simul-create")
    SIMUL_CREATE,

    @SerialName("simul-join")
    SIMUL_JOIN
}

@Serializable
data class TimelineEntriesItemsTimelineEntrySimulData(
    val userId: String,
    val simulId: String,
    val simulName: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryStudyLikeType {
    @SerialName("study-like")
    STUDY_LIKE
}

@Serializable
data class TimelineEntriesItemsTimelineEntryStudyLikeData(
    val userId: String,
    val studyId: String,
    val studyName: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryPlanStartType {
    @SerialName("plan-start")
    PLAN_START
}

@Serializable
data class TimelineEntriesItemsTimelineEntryPlanStartData(
    val userId: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryPlanRenewType {
    @SerialName("plan-renew")
    PLAN_RENEW
}

@Serializable
data class TimelineEntriesItemsTimelineEntryPlanRenewData(
    val userId: String,
    val months: Double
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryUblogPostLikeType {
    @SerialName("ublog-post-like")
    UBLOG_POST_LIKE
}

@Serializable
data class TimelineEntriesItemsTimelineEntryUblogPostLikeData(
    val userId: String,
    val id: String,
    val title: String
)

@Serializable
enum class TimelineEntriesItemsTimelineEntryStreamStartType {
    @SerialName("stream-start")
    STREAM_START
}

@Serializable
data class TimelineEntriesItemsTimelineEntryStreamStartData(
    val id: String,
    val title: String? = null
)


@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class TimelineEntriesItems {
    @Serializable
    @SerialName("follow")
    data class TimelineEntryFollow(
        val type: TimelineEntriesItemsTimelineEntryFollowType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryFollowData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("team-join")
    data class TimelineEntryTeamJoin(
        val type: TimelineEntriesItemsTimelineEntryTeamJoinType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryTeamJoinData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("team-create")
    data class TimelineEntryTeamCreate(
        val type: TimelineEntriesItemsTimelineEntryTeamCreateType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryTeamCreateData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("forum-post")
    data class TimelineEntryForumPost(
        val type: TimelineEntriesItemsTimelineEntryForumPostType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryForumPostData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("blog-post")
    data class TimelineEntryBlogPost(
        val type: TimelineEntriesItemsTimelineEntryBlogPostType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryBlogPostData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("ublog-post")
    data class TimelineEntryUblogPost(
        val type: TimelineEntriesItemsTimelineEntryUblogPostType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryUblogPostData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("tour-join")
    data class TimelineEntryTourJoin(
        val type: TimelineEntriesItemsTimelineEntryTourJoinType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryTourJoinData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("game-end")
    data class TimelineEntryGameEnd(
        val type: TimelineEntriesItemsTimelineEntryGameEndType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryGameEndData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("simul-create")
    data class TimelineEntrySimul(
        val type: TimelineEntriesItemsTimelineEntrySimulType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntrySimulData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("study-like")
    data class TimelineEntryStudyLike(
        val type: TimelineEntriesItemsTimelineEntryStudyLikeType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryStudyLikeData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("plan-start")
    data class TimelineEntryPlanStart(
        val type: TimelineEntriesItemsTimelineEntryPlanStartType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryPlanStartData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("plan-renew")
    data class TimelineEntryPlanRenew(
        val type: TimelineEntriesItemsTimelineEntryPlanRenewType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryPlanRenewData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("ublog-post-like")
    data class TimelineEntryUblogPostLike(
        val type: TimelineEntriesItemsTimelineEntryUblogPostLikeType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryUblogPostLikeData
    ) : TimelineEntriesItems()

    @Serializable
    @SerialName("stream-start")
    data class TimelineEntryStreamStart(
        val type: TimelineEntriesItemsTimelineEntryStreamStartType,
        val date: Double,
        val data: TimelineEntriesItemsTimelineEntryStreamStartData
    ) : TimelineEntriesItems()
}

@Serializable
data class TimelineUsersValue(
    val id: String,
    val name: String,
    val title: Title? = null,
    val flair: Flair? = null,
    val patron: Boolean? = null,
    val patronColor: PatronColor? = null
)

@Serializable
data class Timeline(
    val entries: List<TimelineEntriesItems>,
    val users: Map<String, TimelineUsersValue>
)
