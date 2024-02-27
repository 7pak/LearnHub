package com.example.courseapp.navigation


const val VERIFICATION_SOURCE = "source"
const val POST_COURSE_ID = "post_course_id"
const val POST_SECTION_ID = "post_section_id"

const val GET_USER_ID = "get_user_id"
const val GET_COURSE_ID = "get_course_id"
const val GET_SECTION_ID = "get_section_id"

const val FROM_SIGNUP  = "from_signup"
const val FROM_EMAIL_VERIFICATION_PAGE = "from_email_verification_page"
sealed class Screens(val route:String){
    object LoginScreen: Screens(route = "login_screen")
    object SignUpScreen: Screens(route = "signup_screen")
    object VerificationScreen: Screens(route = "verification_screen?source={$VERIFICATION_SOURCE}"){
        fun passOptionalArg(source:String): String {
            return this.route.replace("{$VERIFICATION_SOURCE}",source)
        }
    }
    object EmailVerificationScreen: Screens(route = "email_verification_screen")
    object PasswordResetScreen: Screens(route = "password_reset_screen")



    object AddCourseScreen: Screens(route = "add_course_screen")
    object AddSectionScreen: Screens(route = "add_section_screen/{$POST_COURSE_ID}"){
        fun passCourseId(id: Int): String {
            return this.route.replace("{$POST_COURSE_ID}","$id")
        }
    }
    object AddVideoScreen: Screens(route = "add_video_screen/{$POST_SECTION_ID}"){
        fun passSectionId(id: Int): String {
            return this.route.replace("{$POST_SECTION_ID}","$id")
        }
    }

    object TeacherScreen: Screens(route = "teacher_home_screen/{$GET_USER_ID}"){
        fun passUserId(id: Int): String {
            return this.route.replace("{$GET_USER_ID}","$id")
        }
    }
    object SectionsScreen: Screens(route ="sections_screen/{$GET_COURSE_ID}"){
        fun passCourseId(id: Int): String {
            return this.route.replace("{$GET_COURSE_ID}","$id")
        }
    }
    object DocumentVideosScreen: Screens(route ="video_screen/{$GET_SECTION_ID}"){
        fun passSectionId(id: Int): String {
            return this.route.replace("{$GET_SECTION_ID}","$id")
        }
    }


    object TeacherProfileScreen: Screens(route = "teacher_profile_screen")


    object StudentHomeScreen:Screens(route = "student_home_screen")
    object StudentSearchScreen:Screens(route = "student_search_screen")
    object StudentSubscriptionScreen:Screens(route = "student_subscription_screen")
    object StudentFavoriteScreen:Screens(route = "student_favorite_screen")
    object StudentProfileScreen:Screens(route = "student_profile_screen")
}

