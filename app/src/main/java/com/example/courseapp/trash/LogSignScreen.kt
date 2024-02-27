package com.example.courseapp.trash

//@Composable
//fun LogSignScreen(
//    navController: NavHostController,
//    teacherLogRegisterModel: TeacherLogRegisterModel = hiltViewModel(),
//    tokenViewModel: TokenViewModel = hiltViewModel()
//) {
//    var currentSection by remember { mutableStateOf(AuthSection.LOGIN) }
//    val context = LocalContext.current
//    val lifecycle = LocalLifecycleOwner.current
//    var isLoading by remember {
//        mutableStateOf(false)
//    }

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colors.background)
//    ) {
//        TopSection(currentSection,isLoading){
//            currentSection = it
//        }

//        when (currentSection) {
//            AuthSection.LOGIN -> LoginSection(navController, teacherLogRegisterModel, tokenViewModel = tokenViewModel, lifecycle, context){isLoading = it}
//            AuthSection.SIGN_UP -> SignUpSection(navController, teacherLogRegisterModel, tokenViewModel = tokenViewModel, lifecycle, context){isLoading = it}
//        }
//    }
//}

//@Composable
//fun TopSection(currentSection: AuthSection,isLoading:Boolean,onCurrentSection:(AuthSection)->Unit) {
//
//    Box(
//        modifier = Modifier
//            .height(250.dp)
//            .fillMaxWidth()
//            .clip(shape = RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp))
//            .background(MaterialTheme.colors.primaryVariant)
//    ) {
//        Text(
//            text = "Coursepp",
//            style = MaterialTheme.typography.h4,
//            fontWeight = FontWeight.SemiBold,
//            color = MaterialTheme.colors.onPrimary,
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(vertical = 10.dp)
//        )
//        if (isLoading){
//            CircularProgressIndicator(color = MaterialTheme.colors.background,modifier = Modifier.align(
//                Alignment.Center))
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter),
//            verticalAlignment = Alignment.Bottom,
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            AuthButton(
//                text = "Login",
//                selected = currentSection == AuthSection.LOGIN,
//                onClick = { onCurrentSection(AuthSection.LOGIN) }
//            )
//            AuthButton(
//                text = "SignUp",
//                selected = currentSection == AuthSection.SIGN_UP,
//                onClick = { onCurrentSection(AuthSection.SIGN_UP) }
//            )
//        }
//    }
//}

//@Composable
//fun AuthButton(text: String, selected: Boolean, onClick: () -> Unit) {
//    Button(
//        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(
//            backgroundColor = if (selected) MaterialTheme.colors.background else Color.Transparent,
//            contentColor = if (selected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.onPrimary
//        ),
//        elevation = ButtonDefaults.elevation(0.dp),
//        modifier = Modifier
//            .clip(shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
//            .width(130.dp)
//    ) {
//        Text(
//            text = text,
//            fontWeight = FontWeight.Bold,
//            style = MaterialTheme.typography.body2
//        )
//    }
//}


//
//
//
//
//
//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun LoginPreview() {
//    CourseAppTheme {
//        LogSignScreen(rememberNavController())
//    }
//}