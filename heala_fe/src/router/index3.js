import Signup from "@/views/user/Signup"
import Login from "@/views/user/Login"
import SignupSuccess from "@/views/user/SignupSuccess"
import Withdrawal from "@/views/user/Withdrawal"
import WithMain from "@/views/challenge/with/WithMain"
import WithMake from "@/views/challenge/with/WithMake"
import WithDetail from "@/views/challenge/with/WithDetail"
import WithDetailCertify from "@/views/challenge/with/WithDetailCertify"
import WithDetailReview from "@/views/challenge/with/WithDetailReview"
import WithMainDiet from "@/views/challenge/with/WithMainDiet"
import WithMainHeart from "@/views/challenge/with/WithMainHeart"
import Profile from "@/views/my_page/Profile";
import ProfilePhysInfo from "@/views/my_page/ProfilePhysInfo";
import Scrap from "@/views/my_page/showmore/Scrap";
import ChangePassword from "@/views/my_page/showmore/ChangePassword";
import DietRecord from "@/views/my_page/record/DietRecord";
import DietRecordDetail from "@/views/my_page/record/DietRecordDetail";
import DietRecordCreate from "@/views/my_page/record/DietRecordCreate";
import DietRecordCalender from "@/views/my_page/record/DietRecordCalender";
import DietRecordPast from "@/views/my_page/record/DietRecordPast";
import WeightCalender from "@/views/my_page/record/WeightCalender";
import WeightRecordPast from "@/views/my_page/record/WeightRecordPast";
import KiwiMain from "@/views/challenge/kiwi/KiwiMain"
import KiwiHealth from "@/views/challenge/kiwi/KiwiHealth"
import KiwiDiet from "@/views/challenge/kiwi/KiwiDiet"
import KiwiHeart from "@/views/challenge/kiwi/KiwiHeart"
import KiwiSpecial from "@/views/challenge/kiwi/KiwiSpecial"
import MyMain from "@/views/challenge/my/MyMain"
// import SendToMain from "@/components/SendToMain"
import OCRTest from "@/components/my_page/profile/OCRTest"
import PageNotFound from "@/components/home/PageNotFound"

export default [
  {
    path: "/",
    redirect: '/user/login'
    // name: "SendToMain",
    // component: SendToMain,
  },
  {
    path: "/user/signup",
    name: "Signup",
    component: Signup,
  },
  {
    path: "/user/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/user/signupsuccess",
    name: "SignupSuccess",
    component: SignupSuccess,
  },
  {
    path: "/user/withdrawal",
    name: "Withdrawal",
    component: Withdrawal,
  },
  {
    path: "/challenge/with/withmain",
    name: "WithMain",
    component: WithMain,
  },
  {
    path: "/challenge/with/withmake",
    name: "WithMake",
    component: WithMake,
  },
  {
    path: "/challenge/with/withmaindiet",
    name: "WithMainDiet",
    component: WithMainDiet,
  },
  {
    path: "/challenge/with/withmainheart",
    name: "WithMainHeart",
    component: WithMainHeart,
  },            
  {
    path: "/challenge/with/:id",
    name: "WithDetail",
    component: WithDetail,
  },
  {
    path: "/challenge/with/:id/certify",
    name: "WithDetailCertify",
    component: WithDetailCertify,
  },
  {
    path: "/challenge/with/:id/review",
    name: "WithDetailReview",
    component: WithDetailReview,
  },
  {
    path: "/profile/:id",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/profile/physinfo",
    name: "ProfilePhysInfo",
    component: ProfilePhysInfo,
  },
  {
    path: "/profile/scrap",
    name: "Scrap",
    component: Scrap,
  },
  {
    path: "/profile/changepassword",
    name: "ChangePassword",
    component: ChangePassword,
  },
  {
    path: "/profile/dietrecord/:id",
    name: "DietRecord",
    component: DietRecord,
  },
  {
    path: "/profile/dietrecorddetail/:id",
    name: "DietRecordDetail",
    component: DietRecordDetail,
  },
  {
    path: "/profile/dietrecordcreate/:id",
    name: "DietRecordCreate",
    component: DietRecordCreate,
  },
  {
    path: "/profile/dietrecordcalender/:id",
    name: "DietRecordCalender",
    component: DietRecordCalender,
  },
  {
    path: "/profile/dietrecordPast/:id/:year/:month/:day",
    name: "DietRecordPast",
    component: DietRecordPast,
  },
  {
    path: "/profile/weightcalender/:id",
    name: "WeightCalender",
    component: WeightCalender,
  },
  {
    path: "/profile/weightpastrecord/:id",
    name: "WeightRecordPast",
    component: WeightRecordPast,
  },
  {
    path: "/challenge/kiwimain",
    name: "KiwiMain",
    component: KiwiMain,
  },
  {
    path: "/challenge/kiwihealth",
    name: "KiwiHealth",
    component: KiwiHealth,
  },
  {
    path: "/challenge/kiwidiet",
    name: "KiwiDiet",
    component: KiwiDiet,
  },
  {
    path: "/challenge/kiwiheart",
    name: "KiwiHeart",
    component: KiwiHeart,
  },
  {
    path: "/challenge/kiwispecial",
    name: "KiwiSpecial",
    component: KiwiSpecial,
  },
  {
    path: "/challenge/my/mymain",
    name: "MyMain",
    component: MyMain,
  },
  {
    path: "/test/OCRTest",
    name: "OCRTest",
    component: OCRTest,
  },
  {
    path: "/*",
    name: "PageNotFound",
    component: PageNotFound,
  },
]