package com.softradix.tinderjetpackdemo.modelClass

import com.google.gson.annotations.SerializedName

data class SignUpResponse(

    @field:SerializedName("subscription_info")
    val subscriptionInfo: SubscriptionInfo? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("token")
    val token: String? = null
)

data class UserFeaturesStatus(

    @field:SerializedName("available_boost")
    val availableBoost: Int? = null,

    @field:SerializedName("is_active")
    val isActive: Int? = null,

    @field:SerializedName("super_likes_reset_on")
    val superLikesResetOn: String? = null,

    @field:SerializedName("available_last_likes")
    val availableLastLikes: Int? = null,

    @field:SerializedName("available_super_likes")
    val availableSuperLikes: Int? = null,

    @field:SerializedName("visible_top_picks")
    val visibleTopPicks: Int? = null,

    @field:SerializedName("boost_reset_on")
    val boostResetOn: String? = null,

    @field:SerializedName("subscription_id")
    val subscriptionId: Int? = null,

    @field:SerializedName("available_likes")
    val availableLikes: Int? = null,

    @field:SerializedName("last_super_liked_on")
    val lastSuperLikedOn: String? = null,

    @field:SerializedName("top_picked_reset_on")
    val topPickedResetOn: String? = null,

    @field:SerializedName("last_boosted_on_ms")
    val lastBoostedOnMs: Int? = null,

    @field:SerializedName("rewind")
    val rewind: Int? = null,

    @field:SerializedName("likes_reset_on")
    val likesResetOn: String? = null,

    @field:SerializedName("last_boosted_on")
    val lastBoostedOn: String? = null,

    @field:SerializedName("available_top_picked")
    val availableTopPicked: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("last_liked_on")
    val lastLikedOn: String? = null
)

data class InterestsItem(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null
)

data class SubscriptionPlan(

    @field:SerializedName("super_likes_duration")
    val superLikesDuration: Int? = null,

    @field:SerializedName("likes_duration")
    val likesDuration: Int? = null,

    @field:SerializedName("top_picks_visible")
    val topPicksVisible: Int? = null,

    @field:SerializedName("top_picks_count")
    val topPicksCount: Int? = null,

    @field:SerializedName("passport")
    val passport: Int? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("product_id")
    val productId: String? = null,

    @field:SerializedName("boost_duration")
    val boostDuration: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("top_picks_duration")
    val topPicksDuration: Int? = null,

    @field:SerializedName("top_picks")
    val topPicks: Int? = null,

    @field:SerializedName("unlimited_likes")
    val unlimitedLikes: Int? = null,

    @field:SerializedName("super_likes")
    val superLikes: Int? = null,

    @field:SerializedName("is_active")
    val isActive: Int? = null,

    @field:SerializedName("priority_likes")
    val priorityLikes: Int? = null,

    @field:SerializedName("apple_id")
    val appleId: Int? = null,

    @field:SerializedName("last_likes_duration")
    val lastLikesDuration: Int? = null,

    @field:SerializedName("plan_name")
    val planName: String? = null,

    @field:SerializedName("grace_period")
    val gracePeriod: Int? = null,

    @field:SerializedName("offer_price")
    val offerPrice: Int? = null,

    @field:SerializedName("likes_count")
    val likesCount: Int? = null,

    @field:SerializedName("last_likes")
    val lastLikes: Int? = null,

    @field:SerializedName("attach_message")
    val attachMessage: Int? = null,

    @field:SerializedName("super_likes_count")
    val superLikesCount: Int? = null,

    @field:SerializedName("ads")
    val ads: Int? = null,

    @field:SerializedName("see_who_likes_me")
    val seeWhoLikesMe: Int? = null,

    @field:SerializedName("unlimited_rewinds")
    val unlimitedRewinds: Int? = null,

    @field:SerializedName("boost")
    val boost: Int? = null,

    @field:SerializedName("android_id")
    val androidId: Int? = null,

    @field:SerializedName("boost_count")
    val boostCount: Int? = null
)

data class SubscriptionInfo(

    @field:SerializedName("transaction_id")
    val transactionId: Int? = null,

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("is_active")
    val isActive: Int? = null,

    @field:SerializedName("is_in_intro_offer_period")
    val isInIntroOfferPeriod: Int? = null,

    @field:SerializedName("user_features_status")
    val userFeaturesStatus: UserFeaturesStatus? = null,

    @field:SerializedName("consumables")
    val consumables: List<ConsumablesItem?>? = null,

    @field:SerializedName("is_trial_period")
    val isTrialPeriod: Int? = null,

    @field:SerializedName("platform")
    val platform: Int? = null,

    @field:SerializedName("subscription_plan")
    val subscriptionPlan: SubscriptionPlan? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("product_id")
    val productId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("plan_id")
    val planId: Int? = null
)

data class User(

    @field:SerializedName("interested_in")
    val interestedIn: Int? = null,

    @field:SerializedName("gender")
    val gender: Int? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("virtual_longitude")
    val virtualLongitude: Double? = null,

    @field:SerializedName("show_my_gender")
    val showMyGender: Int? = null,

    @field:SerializedName("type")
    val type: Int? = null,

    @field:SerializedName("content_language")
    val contentLanguage: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("is_online")
    val isOnline: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null,

    @field:SerializedName("virtual_city")
    val virtualCity: String? = null,

    @field:SerializedName("remaining_super_like_count")
    val remainingSuperLikeCount: Int? = null,

    @field:SerializedName("virtual_latitude")
    val virtualLatitude: Double? = null,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: String? = null,

    @field:SerializedName("otp")
    val otp: Int? = null,

    @field:SerializedName("app_notification")
    val appNotification: Int? = null,

    @field:SerializedName("is_verified")
    val isVerified: Int? = null,

    @field:SerializedName("otp_expiration_time")
    val otpExpirationTime: String? = null,

    @field:SerializedName("is_blocked")
    val isBlocked: Int? = null,

    @field:SerializedName("is_premium")
    val isPremium: Int? = null,

    @field:SerializedName("email_verification_token")
    val emailVerificationToken: Double? = null,

    @field:SerializedName("dob")
    val dob: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("interests")
    val interests: List<InterestsItem?>? = null,

    @field:SerializedName("age")
    val age: Int? = null,

    @field:SerializedName("user_images")
    val userImages: List<UserImagesItem?>? = null
)

data class UserImagesItem(

    @field:SerializedName("image_name")
    val imageName: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("image_path")
    val imagePath: String? = null,

    @field:SerializedName("is_main")
    val isMain: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class ConsumablesItem(

    @field:SerializedName("consumable_type")
    val consumableType: String? = null,

    @field:SerializedName("is_active")
    val isActive: Int? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("platform")
    val platform: Int? = null,

    @field:SerializedName("consumable_quantity")
    val consumableQuantity: Int? = null
)
