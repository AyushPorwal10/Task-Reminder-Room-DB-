package com.example.checkingagp.mydata

data class FullPosition(
    val companyId: Int,
    val companyIndustry: String,
    val companyLogo: String,
    val companyName: String,
    val companyStaffCountRange: String,
    val companyURL: String,
    val companyUsername: String,
    val description: String,
    val employmentType: String,
    val end: End,
    val location: String,
    val multiLocaleCompanyName: MultiLocaleCompanyName,
    val multiLocaleTitle: MultiLocaleTitle,
    val start: Start,
    val title: String
)