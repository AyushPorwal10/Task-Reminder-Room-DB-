package com.example.checkingagp

import com.example.checkingagp.mydata.BackgroundImage
import com.example.checkingagp.mydata.Education
import com.example.checkingagp.mydata.FullPosition
import com.example.checkingagp.mydata.Geo
import com.example.checkingagp.mydata.MultiLocaleFirstName
import com.example.checkingagp.mydata.MultiLocaleHeadline
import com.example.checkingagp.mydata.MultiLocaleLastName
import com.example.checkingagp.mydata.Position
import com.example.checkingagp.mydata.ProfilePicture
import com.example.checkingagp.mydata.Projects
import com.example.checkingagp.mydata.Skill
import com.example.checkingagp.mydata.SupportedLocale

data class MainUserProfileData(
    val backgroundImage: List<BackgroundImage>,
    val educations: List<Education>,
    val firstName: String,
    val fullPositions: List<FullPosition>,
    val geo: Geo,
    val headline: String,
    val id: Int,
    val isCreator: Boolean,
    val isPremium: Boolean,
    val isTopVoice: Boolean,
    val lastName: String,
    val multiLocaleFirstName: MultiLocaleFirstName,
    val multiLocaleHeadline: MultiLocaleHeadline,
    val multiLocaleLastName: MultiLocaleLastName,
    val position: List<Position>,
    val profilePicture: String,
    val profilePictures: List<ProfilePicture>,
    val projects: Projects,
    val skills: List<Skill>,
    val summary: String,
    val supportedLocales: List<SupportedLocale>,
    val urn: String,
    val username: String
)