package com.wasitech.permissioncenter.java.com.wasitech.assist.actions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import java.util.List;

import com.wasitech.basics.classes.Issue;

public class Open extends ActivityCompat {
    private Intent intent;
    final Context context;
    private final PackageManager pk;
    private String txtBack;
    private String packName;
    public Open(Context context){
        this.pk=context.getPackageManager();
        this.context=context;
    }
    public void setData(String str){

        if(intent==null){
            callThirdParty(str);
        }
        if(intent==null){
            accordingPhone(str);
        }
        if(intent==null){
            callAndroid(str);
        }
        if(intent==null){
            callGoogle(str);
        }
        if(intent==null){
            phoneAccessing(str);
        }
    }
    public String openApps(String str){
        setData(str);
        if(intent==null){
            return notFound();
        } else {
            startActivity(context,intent,null);
        }
        return "Opening "+txtBack+".";
    }
    public boolean social(String name){
        intent=thirdParty(name);
        if(intent!=null){
            startActivity(context,intent,null);
            return true;
        }
        return false;
    }

    private void phoneAccessing(String str) {
        @SuppressLint("QueryPermissionsNeeded")
        List<ApplicationInfo> packages = pk.getInstalledApplications(PackageManager.GET_META_DATA);
        String name;
        for (ApplicationInfo packageInfo : packages) {
            name= (String) pk.getApplicationLabel(packageInfo);
            intent= pk.getLaunchIntentForPackage(packageInfo.packageName);
            if(str.contains(name.toLowerCase())||str.equalsIgnoreCase(name)){
                if(intent!=null){
                    txtBack=name;
                    this.packName=packageInfo.packageName;
                    break;
                }
            }
        }
    }
    private void accordingPhone(String str) {
        String manu = Build.MANUFACTURER.toLowerCase();
        switch (manu) {
            case "samsung": {
                callSamsung(str);
                break;
            }
            case "vivo": {
                callVivo(str);
                break;
            }
            case "xiaomi": {
                callXiaomi(str);
                break;
            }
            case "oneplus": {
                callOnePlus(str);
                break;
            }
            case "asus": {
                callAsus(str);
                break;
            }
            case "nokia": {
                callNokia(str);
                break;
            }
            case "huawei": {
                callHuawei(str);
                break;
            }
            // case "oppo":        {callOppo(str);break;}
            // case "qmobile":     {callQMobile(str);break;}
        }
    }

    private void callThirdParty(String str){
        if(str.contains("whatsapp")&&(str.contains("arow")||str.contains("my")||str.contains("arrow")||str.contains("aero"))){
            intent=thirdParty("aero whatsapp");
        }
        else if (str.contains("whatsapp")&&str.contains("business")){
            intent=thirdParty("whatsapp business");
        }
        else if (str.contains("amazon")){
            intent=thirdParty("amazon");
        }
        else if (str.contains("e bay")&&str.contains("ebay")){
            intent=thirdParty("ebay");
        }
        else if (str.contains("booking")&&str.contains("com")){
            intent=thirdParty("booking.com");
        }
        else if (str.contains("whatsapp")&&str.contains("gb")){
            intent=thirdParty("gb whatsapp");
        }
        else if (str.contains("whatsapp")){
            intent=thirdParty("whatsapp");
        }
        else if (str.contains("shareit")){
            intent=thirdParty("shareit");
        }
        else if (str.contains("daraz")||str.contains("the raaz")){
            intent=thirdParty("daraz.pk");
        }
        else if (str.contains("my")&&str.contains("telenor")){
            intent=thirdParty("telenor");
        }
        else if (str.contains("my")&&str.contains("zong")){
            intent=thirdParty("zong");
        }
        else if (str.contains("fire")&&str.contains("fox")){
            intent=thirdParty("firefox");
        }
        else if (str.contains("messenger")&&!str.contains("dual")){
            intent=thirdParty("messenger");
        }
        else if (str.contains("instagram")){
            intent=thirdParty("instagram");
        }
        else if (str.contains("facebook")){
            intent=thirdParty("facebook");
        }
        else if (str.contains("food")&&str.contains("panda")){
            intent=thirdParty("foodpanda");
        }
        else if (str.contains("easypaisa")){
            intent=thirdParty("easypaisa");
        }
        else if (str.contains("snapchat")){
            intent=thirdParty("snapchat");
        }
        else if (str.contains("skype")){
            intent=thirdParty("skype");
        }
        else if (str.contains("jazz")){
            if (str.contains("cash")){
                intent=thirdParty("jazz cash");
            }else if (str.contains("world")){
                intent=thirdParty("jazz world");
            }
        }
        else if (str.contains("opera")){
            if (str.contains("mini")){
                intent=thirdParty("opera mini");
            }else{
                intent=thirdParty("opera");
            }
        }
        else if (str.contains("inshot")||str.contains("in short")){
            intent=thirdParty("inshot");
        }
        else if (str.contains("vidmate")){
            intent=thirdParty("vidmate");
        }
        else if (str.contains("pinterest")){
            intent=thirdParty("pinterest");
        }
        else if (str.contains("phoenix")){
            intent=thirdParty("phoenix");
        }
        else if (str.contains("zoom")){
            intent=thirdParty("zoom");
        }
        else if (str.contains("cinema hd")){
            intent=thirdParty("cinema");
        }
        else if (str.contains("shazam")){
            intent=thirdParty("shazam");
        }
        else if (str.contains("dropbox")){
            intent=thirdParty("dropbox");
        }
        else if (str.contains("tiktok")||str.contains("tik tok")){
            intent=thirdParty("tiktok");
        }
        else if (str.contains("uber")){
            intent=thirdParty("uber");
        }
        else if (str.contains("twitter")){
            intent=thirdParty("twitter");
        }
        else if (str.contains("muslim pro")){
            intent=thirdParty("muslim pro");
        }
        else if (str.contains("netflix")){
            intent=thirdParty("netflix");
        }
        else if (str.contains("saavn")||str.contains("jio saavn")){
            intent=thirdParty("saavan");
        }
        else if (str.contains("barcode scanner")){
            intent=thirdParty("barcode scanner");
        }
        else if (str.contains("mcb")||str.contains("m c b")){
            intent=thirdParty("mcb");
        }
        else if (str.contains("hbl")||str.contains("h b l")){
            intent=thirdParty("hbl");
        }
        else if (str.contains("meezan")||str.contains("mizan")){
            intent=thirdParty("meezan");
        }
        else if (str.contains("ubl")||str.contains("u b l")){
            intent=thirdParty("ubl");
        }
        else if (str.contains("askari")||str.contains("aas kari")){
            intent=thirdParty("askari");
        }
        else if (str.contains("abl")||str.contains("a b l")){
            intent=thirdParty("abl");
        }
        else if (str.contains("adobe")&&str.contains("reader")){
            intent=thirdParty("adobe reader");
        }
        else if (str.contains("ali")&&str.contains("express")){
            intent=thirdParty("ali express");
        }
        else if (str.contains("snap")&&str.contains("seed")){
            intent=thirdParty("snapseed");
        }
        else if (str.contains("overlay")&&(str.contains("video"))||str.contains("image")){
            intent=thirdParty("overlay video image");
        }
        else if (str.contains("my")&&str.contains("video")){
            intent=thirdParty("my video");
        }
        else if ((str.contains("fb")||str.contains("facebook"))&&str.contains("video")&&str.contains("download")){
            intent=thirdParty("fb video downloader");
        }
        else if (str.contains("smart")&&str.contains("tutor")){
            intent=thirdParty("smart tutor");
        }
        else if (str.contains("pen")&&str.contains("up")){
            intent=thirdParty("penup");
        }
        else if (str.contains("print")){
            intent=thirdParty("print");
        }
        else if ((str.contains("art")||str.contains("8"))&&str.contains("cover")){
            intent=thirdParty("art cover");
        }
        else if (str.contains("cam")&&str.contains("scan")){
            intent=thirdParty("cam scanner");
        }
        else if (str.contains("s10")&&str.contains("camera")){
            intent=thirdParty("one s10 camera");
        }
        else if (str.contains("cm")&&str.contains("browser")){
            intent=thirdParty("cm browser");
        }
        else if (str.contains("galaxy")&&str.contains("series")){
            intent=thirdParty("galaxy series");
        }
        else if (str.contains("mx")&&str.contains("player")){
            intent=thirdParty("mx player");
        }
        else if (str.contains("tv")&&str.contains("remote")){
            intent=thirdParty("tv remote");
        }
        else if (str.contains("team")&&str.contains("viewer")){
            intent=thirdParty("team viewer");
        }
        else if (str.contains("wps")&&str.contains("office")){
            intent=thirdParty("wps office");
        }
        else if (str.contains("uc")&&str.contains("browser")){
            intent=thirdParty("uc browser");
        }
        else if (str.contains("uc")&&str.contains("mini")){
            intent=thirdParty("uc mini");
        }
        else if (str.contains("workout")&&str.contains("trainer")){
            intent=thirdParty("workout trainer");
        }
        else if (str.contains("yahoo")&&str.contains("mail")){
            intent=thirdParty("yahoo mail");
        }
        else if (str.contains("wego")&&(str.contains("flight"))||str.contains("hotel")){
            intent=thirdParty("wego");
        }
        else if (str.contains("olx")){
            intent=thirdParty("olx");
            if(intent==null)
                intent=thirdParty("olx.pk");
        }
        else if (str.contains("sonic")&&(str.contains("run"))||str.contains("runner")){
            intent=thirdParty("sonic runner");
        }
        else if (str.contains("spider")&&str.contains("man")&&(str.contains("ultimate"))||str.contains("power")){
            intent=thirdParty("spider man ultimate");
        }
        else if (str.contains("asphalt")&&str.contains("nitro")){
            intent=thirdParty("asphalt nitro");
        }
        else if (str.contains("assassin")&&str.contains("creed")&&(str.contains("unity"))||str.contains("arno")){
            intent=thirdParty("assassin creed");
        }
        else if (str.contains("puzzle")&&str.contains("pets")){
            intent=thirdParty("puzzlepets");
        }
        else if (str.contains("kingdoms")&&str.contains("s")){
            intent=thirdParty("kingdoms");
        }
        else if (str.contains("dragon")&&str.contains("mania")){
            intent=thirdParty("dragon mania");
        }
        else if (str.contains("lords")&&str.contains("mobile")){
            intent=thirdParty("lords mobile");
        }
    }
    private Intent thirdParty(String str){
        String name="";
        switch (str){
            case "youtube":             {name="com.google.android.youtube";break;}
            case "amazon":              {name="com.amazon.aa";break;}
            case "amazon attribution":  {name="com.amazon.aa.attribution";break;}
            case "booking.com":         {name="com.booking";break;}
            case "ebay":                {name="com.ebay.mobile";break;}
            case "messenger":           {name="com.facebook.orca";break;}
            case "facebook":            {name="com.facebook.katana";break;}
            case "fb service":          {name="com.facebook.services";break;}
            case "instagram":           {name="com.instagram.android";break;}
            case "netflix":             {name="com.netflix.mediaclient";break;}
            case "netflix partner":     {name="com.netflix.partner.activation";break;}
            case "microsoft translator":{name="com.microsoft.translator";break;}
            case "snapchat":            {name="com.snapchat.android";break;}
            case "swiftkeycof":         {name="com.swiftkey.swiftkeyconfigurator";break;}
            case "swiftkey":            {name="com.touchtype.swiftkey";break;}
            case "twitter":             {name="com.twitter.android";break;}
            case "smsallience":         {name="org.smsalliance.openmobileapi.service";break;}
            case "aero whatsapp":       {name="com.aero";break;}
            case "whatsapp":            {name="com.whatsapp";break;}
            case "whatsapp business":   {name="com.whatsapp.w4b";break;}
            case "gb whatsapp":         {name="com.gbwhatsapp";break;}
            case "shareit":             {name="com.lenovo.anyshare.gps";break;}
            case "daraz.pk":            {name="com.daraz.android";break;}
            case "telenor":             {name="com.telenor.pakistan.mytelenor";break;}
            case "zong":                {name="com.zong.customercare";break;}
            case "firefox":             {name="org.mozilla.firefox";break;}
            case "foodpanda":           {name="com.global.foodpanda.android";break;}
            case "easypaisa":           {name="pk.com.telenor.phoenix";break;}
            case "skype":               {name="com.skype.raider";break;}
            case "jazz cash":           {name="com.techlogix.mobilinkcustomer";break;}
            case "jazz world":          {name="com.jazz.jazzworld";break;}
            case "opera mini":          {name="com.opera.mini.android";break;}
            case "opera":               {name="com.opera.browser";break;}
            case "inshot":              {name="com.camerasideas.instashot";break;}
            case "vidmate":             {name="com.nemo.vidmate";break;}
            case "pinterest":           {name="com.pinterest";break;}
            case "phoenix":             {name="com.transsion.phoenix";break;}
            case "zoom":                {name="us.zoom.videomeetings";break;}
            case "cinema":              {name="com.yoku.marumovie";break;}
            case "shazam":              {name="com.shazam.android";break;}
            case "dropbox":             {name="com.dropbox.android";break;}
            case "tiktok":              {name="com.zhiliaoapp.musically";break;}
            case "uber":                {name="com.ubercab";break;}
            case "muslim pro":          {name="com.bitsmedia.android.muslimpro";break;}
            case "saavan":              {name="com.jio.media.jiobeats";break;}
            case "barcode scanner":     {name="com.google.zxing.client.android";break;}
            case "mcb":                 {name="com.eaccess.mcbmobile";break;}
            case "hbl":                 {name="com.hbl.android.hblmobilebanking";break;}
            case "meezan":              {name="invo8.meezan.mb";break;}
            case "ubl":                 {name="app.com.brd";break;}
            case "askari":              {name="com.askari";break;}
            case "abl":                 {name="com.ofss.digx.mobile.android.allied";break;}
            case "adobe reader":        {name="com.adobe.reader";break;}
            case "ali express":         {name="com.alibaba.aliexpresshd";break;}
            case "snapseed":            {name="com.niksoftware.snapeed";break;}
            case "overlay video image": {name="com.bongasoft.overlayvideoimage";break;}
            case "my video":            {name="com.sec.kidsplat.media.kidsmedia";break;}
            case "fb video downloader": {name="videodownloader.fbvideodownloader.facebookvideodownloader.videodownloaderforfacebook";break;}
            case "smart tutor":         {name="com.rsupport.rs.activity.rsupport.ass2";break;}  ///// o
            case "penup":               {name="com.sec.penup";break;}
            case "print":               {name="com.sec.print.mobileprint";break;}
            case "art cover":           {name="com.progmaatic.samartcover";break;}
            case "cam scanner":         {name="com.intsig.camscanner";break;}
            case "one s10 camera":      {name="com.camera.one.s10.camera";break;}
            case "cm browser":          {name="com.ksmobile.cb";break;}
            case "galaxy series":       {name="com.vintedge.windchimes";break;}
            case "mx player":           {name="com.mxtech.videoplayer.ad";break;}
            case "tv remote":           {name="roid.spikesroid.sam_voice_remote";break;}
            case "team viewer":         {name="com.teamviewer.teamviewer.market.mobile";break;}
            case "wps office":          {name="cn.wps.moffice_i18n";break;}
            case "uc browser":          {name="com.UCMobile.intl";break;}
            case "uc mini":             {name="com.uc.browser.en";break;}
            case "workout trainer":     {name="com.skimble.workouts";break;}
            case "yahoo mail":          {name="com.yahoo.mobile.client.android.mail";break;}
            case "wego":                {name="com.wego.android";break;}
            case "olx.pk":              {name="com.olx.pk";break;}
            case "olx":                 {name="com.olx";break;}
            case "sonic runner":        {name="com.gameloft.android.GloftSOMP";break;}
            case "asphalt nitro":       {name="com.gameloft.android.GloftANPH";break;}
            case "spider man ultimate": {name="com.gameloft.android.GloftSMIF";break;}
            case "dragon mania":        {name="com.gameloft.android.GloftPDMF";break;}
            case "kingdoms":            {name="com.gameloft.android.GloftDMKF";break;}
            case "puzzlepets":           {name="com.gameloft.android.GloftDBMF";break;}
            case "lords mobile":        {name="com.igg.android.lordsmobile";break;}
            case "assassin creed":      {name="com.playwing.acu.huawei";break;}
            case "":        {name="";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return  pk.getLaunchIntentForPackage(name);
    }

    private void callSamsung(String str) {
        if (str.contains("contact")||str.contains("contacts")){
            intent=samsung("contact");
        }
        else if (str.contains("one")&&str.contains("hand")&&str.contains("mod")){
            intent=samsung("one hand mode");
        }
        else if (str.contains("galaxy")&&str.contains("friend")){
            intent=samsung("galaxy friends");
        }
        else if (str.contains("camera")){
            intent=samsung("camera");
        }
        else if (str.contains("smart")&&str.contains("switch")){
            intent=samsung("smart switch");
        }
        else if (str.contains("smart")&&str.contains("thing")){
            intent=samsung("smart things");
        }
        else if (str.contains("samsung")&&str.contains("members")){
            intent=samsung("samsung members");
        }
        else if (str.contains("car")&&str.contains("mode")){
            intent=samsung("car mode");
        }
        else if (str.contains("gallery")||str.contains("pictures")||str.contains("pics")||str.contains("photos")){
            intent=samsung("gallery");
        }
        else if ((str.contains("kid")||str.contains("game"))&&str.contains("launcher")){
            intent=samsung("kids launcher");
        }
        else if (str.contains("browser")){
            intent=samsung("browser");
        }
        else if (str.contains("email")){
            intent=samsung("email");
        }
        else if (str.contains("galaxy")&&str.contains("watch")){
            intent=samsung("galaxy watch");
        }
        else if (str.contains("calendar")||str.contains("calender")||str.contains("date")){
            intent=samsung("calender");
        }
        else if (str.contains("samsung")&&str.contains("pas")){
            intent=samsung("samsung pass");
        }
        else if (str.contains("dual")&&str.contains("messenger")){
            intent=samsung("dual messenger");
        }
        else if (str.contains("samsung")&&str.contains("pay")){
            intent=samsung("samsung pay");
        }
        else if (str.contains("always")&&str.contains("display")){
            intent=samsung("always on display");
        }
        else if (str.contains("air")&&str.contains("command")){
            intent=samsung("air command");
        }
        else if (str.contains("weather")||str.contains("mousam")){
            intent=samsung("weather");
        }
        else if (str.contains("what")&&str.contains("new")){
            intent=samsung("whats new");
        }
        else if (str.contains("samsung")&&str.contains("shop")){
            intent=samsung("samsung shop");
        }
        else if (str.contains("calculator")){
            intent=samsung("calculator");
        }
        else if (str.contains("music")){
            intent=samsung("music");
        }
        else if (str.contains("file manager")||str.contains("storage")){
            intent=samsung("file");
        }
        else if ((str.contains("voice")||str.contains("sound"))&&str.contains("recorder")){
            intent=samsung("recorder");
        }
        else if (str.contains("sms")||str.contains("message")){
            intent=samsung("sms");
        }
        else if (str.contains("clock")||str.contains("time")){
            intent=samsung("clock");
        }
        else if (str.contains("finder")){
            intent=samsung("finder");
        }
        else if (str.contains("galaxy store")){
            intent=samsung("galaxy store");
        }
        else if (str.contains("theme store")){
            intent=samsung("theme store");
        }
        else if (str.contains("memo")){
            intent=samsung("memo");
        }
        else if (str.contains("account")&&str.contains("samsung")){
            intent=samsung("account");
        }
        else if (str.contains("cloud")){
            intent=samsung("cloud");
        }
        else if (str.contains("health")){
            intent=samsung("health");
        }
        else if (str.contains("with")&&str.contains("tv")){
            intent=samsung("withtv");
        }
        else if (str.contains("talk")&&str.contains("back")){
            intent=samsung("talkback");
        }
        else if (str.contains("video")&&str.contains("player")){
            intent=samsung("video player");
        }
        else if (str.contains("video")&&str.contains("edit")&&str.contains("story")){
            intent=samsung("story video editor");
        }
        else if (str.contains("sound")&&str.contains("alive")){
            intent=samsung("sound alive");
        }
        else if (str.contains("activity")&&str.contains("track")){
            intent=samsung("activity tracker");
        }
        else if (str.contains("s")&&str.contains("note")){
            intent=samsung("s note");
        }
        else if (str.contains("family")&&str.contains("hub")){
            intent=samsung("family hub");
        }
        else if (str.contains("family")&&str.contains("square")){
            intent=samsung("family square");
        }
        else if (str.contains("samsung")&&str.contains("level")){
            intent=samsung("samsung level");
        }
        else if (str.contains("video")&&str.contains("trimmer")){
            intent=samsung("video trimmer");
        }
        else if (str.contains("video")){
            intent=samsung("video");
        }
        else if (str.contains("smart")&&str.contains("call")){
            intent=samsung("smart call");
        }
        else if (str.contains("smart")&&str.contains("home")){
            intent=samsung("smart home");
        }
        else if (str.contains("smart")&&str.contains("view")){
            intent=samsung("smart view");
        }
        else if (str.contains("knox")&&str.contains("deployment")){
            intent=samsung("knox deployment");
        }
        else if (str.contains("audio")&&str.contains("remote")){
            intent=samsung("audio remote");
        }
        else if (str.contains("accessory")){
            intent=samsung("accessory");
        }
        else if (str.contains("battery")&&str.contains("guide")){
            intent=samsung("battery guide");
        }
        else if (str.contains("samsung")&&str.contains("capture")){
            intent=samsung("samsung capture");
        }
        else if (str.contains("samsung")&&str.contains("plus")){
            intent=samsung("samsung plus");
        }
        else if (str.contains("text to")&&str.contains("speech")){
            intent=samsung("text to speech");
        }
        else if (str.contains("system")&&str.contains("nx")){
            intent=samsung("nxsystem kor");
        }
        else if (str.contains("gear")&&str.contains("manager")){
            intent=samsung("gear fit manager");
        }
        else if (str.contains("gear")&&str.contains("360")){
            intent=samsung("samsung gear 360");
        }
        else if (str.contains("tv")&&str.contains("remote")){
            intent=samsung("sam tv remote");
        }
        else if (str.contains("samsung")&&str.contains("incentive")){
            intent=samsung("samsung incentive");
        }
        else if (str.contains("my")&&str.contains("recipe")){
            intent=samsung("my recipe");
        }
        else if (str.contains("notes")){
            intent=samsung("notes");
        }
        else if (str.contains("partner")&&str.contains("lounge")){
            intent=samsung("partner lounge");
        }
        else if (str.contains("portable")&&str.contains("ssd")){
            intent=samsung("portable ssd");
        }
        else if (str.contains("swift")&&str.contains("gift")){
            intent=samsung("swift gift samsung");
        }
        else if (str.contains("samsung")&&str.contains("booking")){
            intent=samsung("booking.com for samsung");
        }
        else if (str.contains("samsung")&&str.contains("font")){
            intent=samsung("font for samsung");
        }
        else if (str.contains("tv")&&str.contains("cast")){
            intent=samsung("tv cast samsung");
        }
        else if (str.contains("samsung")&&(str.contains("scribe"))||str.contains("s crib")){
            intent=samsung("scribd samsung");
        }
        else if (str.contains("samsung")&&str.contains("max")){
            intent=samsung("samsung max");
        }
    }
    private Intent samsung(String str){
        String name="";
        switch (str){
            case "contact":                 {name="com.samsung.android.contacts";break;}
            case "one hand mode":           {name="com.sec.android.easyonehand";break;}    // no
            case "galaxy friends":          {name="com.samsung.android.mateagent";break;}
            case "camera":                  {name="com.sec.android.app.camera";break;}
            case "smart switch":            {name="com.sec.android.easyMover";break;}
            case "smart things":            {name="com.samsung.android.oneconnect";break;}
            case "samsung members":         {name="com.samsung.android.voc";break;}
            case "car mode":                {name="com.samsung.android.drivelink.stub";break;}  // no
            case "gallery":                 {name="com.sec.android.gallery3d";break;}
            case "kids launcher":           {name="com.samsung.android.game.gamehome";break;}
            case "browser":                 {name="com.sec.android.app.sbrowser";break;}
            case "email":                   {name="com.samsung.android.email.provider";break;}
            case "galaxy watch":            {name="com.samsung.android.app.watchmanagerstub";break;}
            case "calender":                {name="com.samsung.android.calendar";break;}
            case "samsung pass":            {name="com.samsung.android.samsungpass";break;}
            case "dual messenger":          {name="com.samsung.android.da.daagent";break;}    // no
            case "samsung pay":             {name="com.samsung.android.spay";break;}     // no
            case "always on display":       {name="com.samsung.android.app.aodservice";break;}     // no
            case "air command":             {name="com.samsung.android.service.aircommand";break;}       // no
            case "weather":                 {name="com.sec.android.daemonapp";break;}
            case "whats new":               {name="com.samsung.android.app.social";break;}     // no
            case "samsung shop":            {name="com.samsung.ecomm.global";break;}     // no
            case "calculator":              {name="com.sec.android.app.popupcalculator";break;}
            case "music":                   {name="com.sec.android.app.music";break;}
            case "file":                    {name="com.sec.android.app.myfiles";break;}
            case "recorder":                {name="com.sec.android.app.voicenote";break;}
            case "sms":                     {name="com.samsung.android.messaging";break;}
            case "clock":                   {name="com.sec.android.app.clockpackage";break;}
            case "finder":                  {name="com.samsung.android.app.galaxyfinder";break;}   // no
            case "galaxy store":            {name="com.sec.android.app.samsungapps";break;}
            case "theme store":             {name="com.samsung.android.themecenter";break;}   // no
            case "memeo":                   {name="com.samsung.android.app.memo";break;}
            case "account":                 {name="com.osp.app.signin";break;}       // no
            case "cloud":                   {name="com.samsung.android.scloud";break;}
            case "health":                  {name="com.sec.android.app.shealth";break;}

            case "withtv":                  {name="com.samsung.android.app.withtv";break;}
            case "talkback":                {name="com.samsung.android.app.talkback";break;}
            case "video player":            {name="com.samsung.android.video";break;}
            case "story video editor":      {name="com.samsung.app.highlightplayer";break;}
            case "sound alive":             {name="";break;}
            case "activity tracker":        {name="com.samsung.android.app.atracker";break;}
            case "s note":                  {name="com.samsung.android.snote";break;}
            case "family hub":              {name="com.samsung.familyhub";break;}
            case "family square":           {name="com.samsung.android.familyshare.mobile";break;}
            case "samsung level":           {name="com.sec.samsungsoundphone";break;}
            case "video trimmer":            {name="com.samsung.app.newtrim";break;}
            case "video":                   {name="com.samsung.android.videolist";break;}
            case "smart call":              {name="com.samsung.android.smartcallprovider";break;}
            case "smart home":              {name="com.samsung.smarthome";break;}
            case "smart view":              {name="com.samsung.smartviewad";break;}
            case "knox deployment":         {name="com.samsung.android.knox.enrollment";break;}
            case "audio remote":            {name="com.samsung.samsungband";break;}
            case "accessory":               {name="com.samsung.accessory";break;}
            case "battery guide":           {name="ke.ac.ilabafrica.samsungbatteryguide";break;}
            case "samsung capture":         {name="com.samsung.android.app.smartcapture";break;}
            case "samsung plus":            {name="com.samsung.plus.mobile";break;}
            case "text to speech":          {name="com.samsung.SMT";break;}
            case "nxsystem kor":            {name="com.samsung.app.nxsystem_gtab_kr";break;}
            case "gear fit manager":        {name="com.samsung.android.wms";break;}
            case "samsung gear 360":        {name="com.samsung.android.gear360manager";break;}
            case "sam tv remote":           {name="com.samremote.view";break;}
            case "samsung incentive":       {name="com.samsungincentivemena";break;}
            case "my recipe":               {name="com.samsung.samsungMyRecipes";break;}
            case "notes":                   {name="com.samsung.android.app.notes";break;}
            case "partner lounge":          {name="com.veleum.samsung.partnerbereich";break;}
            case "portable ssd":            {name="com.samsung.samsungpssdplus";break;}
            case "swift gift samsung":      {name="me.swiftgift.swiftgift.samsung";break;}
            case "booking.com for samsung": {name="com.booking.forsamsung";break;}
            case "font for samsung":        {name="com.prodev.finder";break;}
            case "tv cast samsung":         {name="de.twokit.video.tv.cast.browser.samsungandroidpro";break;}
            case "scribd samsung":          {name="com.scribd.app.samsung";break;}
            case "samsung max":             {name="com.opera.max.global";break;}
            case "":               {name="";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());

            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callVivo(String str) {
        if (str.contains("contact")||str.contains("contacts")){
            intent=vivo("contact");
        }
        else if (str.contains("car")&&str.contains("mode")){
            intent=vivo("car mode");
        }
        else if (str.contains("motor")&&str.contains("mode")){
            intent=vivo("motor mode");
        }
        else if (str.contains("account")){
            intent=vivo("account");
        }
        else if (str.contains("camera")){
            intent=vivo("camera");
        }
        else if (str.contains("translator")){
            intent=vivo("translator");
        }
        else if (str.contains("pay")){
            intent=vivo("pay");
        }
        else if (str.contains("power")&&str.contains("save")){
            intent=vivo("power saver");
        }
        else if (str.contains("gallery")||str.contains("pictures")||str.contains("pics")||str.contains("photos")){
            intent=vivo("gallery");
        }
        else if (str.contains("calendar")||str.contains("calender")||str.contains("date")){
            intent=vivo("calender");
        }
        else if (str.contains("weather")||str.contains("mousam")){
            intent=vivo("weather");
        }
        else if (str.contains("calculator")){
            intent=samsung("calculator");
        }
        else if (str.contains("music")){
            intent=samsung("music");
        }
        else if (str.contains("compass")){
            intent=vivo("compass");
        }
        else if (str.contains("easy")&&str.contains("share")){
            intent=vivo("easy share");
        }
        else if (str.contains("email")){
            intent=vivo("email");
        }
        else if (str.contains("fm")||str.contains("f m")){
            intent=vivo("fm");
        }
        else if (str.contains("cloud")&&str.contains("vivo")){
            intent=vivo("cloud");
        }
        else if ((str.contains("voice")||str.contains("sound"))&&str.contains("recorder")){
            intent=vivo("recorder");
        }
        else if (str.contains("playstore")||str.contains("play store")||str.contains("appstore")||str.contains("app store")){
            intent=vivo("playstore");
        }
    }
    private Intent vivo(String str){
        String name="";
        switch (str){
            case "contact":{name="com.vivo.sim.contacts";break;}
            case "car mode":{name="com.vivo.carmode";break;}
            case "motor mode":{name="com.vivo.motormode";break;}
            case "account":{name="com.bbk.account";break;}
            case "camera":{name="";break;}
            case "translator":{name="com.vivo.translator";break;}
            case "pay":{name="com.vivo.unionpay";break;}
            case "power saver":{name="com.bbk.SuperPowerSave";break;}
            case "gallery":{name="com.vivo.gallery";break;}
            case "calender":{name="com.bbk.calendar";break;}
            case "weather":{name="com.vivo.weather";break;}
            case "calculator":{name="com.android.bbkcalculator";break;}
            case "music":{name="com.android.bbkmusic";break;}
            case "compass":{name="com.vivo.compass";break;}
            case "easy share":{name="com.vivo.easyshare";break;}
            case "email":{name="com.vivo.email";break;}
            case "fm":{name="com.vivo.FMRadio";break;}
            case "cloud":{name="com.bbk.cloud";break;}
            case "recorder":{name="com.android.bbksoundrecorder";break;}
            case "playstore":{name="com.vivo.appstore";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callOnePlus(String str) {
        if (str.contains("pictures")||str.contains("pics")||str.contains("photos")){
            intent=onePlus("photos");
        }
        else if (str.contains("file manager")||str.contains("storage")){
            intent=onePlus("file");
        }
        else if (str.contains("gallery")){
            intent=onePlus("gallery");
        }
        else if (str.contains("mms")){
            intent=onePlus("mms");
        }
        else if (str.contains("screen")&&str.contains("record")){
            intent=onePlus("screen recorder");
        }
        else if (str.contains("screen")&&str.contains("mirror")){
            intent=onePlus("screen mirroring");
        }
        else if (str.contains("wallpaper")){
            intent=onePlus("wallpaper");
        }
    }
    private Intent onePlus(String str){
        String name="";
        switch (str){
            case "photos":{name="cn.oneplus.photos";break;}
            case "file":{name="com.oneplus.filemanager";break;}
            case "gallery":{name="com.oneplus.gallery";break;}
            case "mms":{name="com.oneplus.mms";break;}
            case "screen recorder":{name="com.oneplus.screenrecord";break;}
            case "screen mirroring":{name="com.oneplus.tvcast";break;}
            case "wallpaper":{name="com.oneplus.wallpaper";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callAsus(String str){
        if (str.contains("keyboard")||(str.contains("key")&&str.contains("board"))){
            intent=asus("keyboard");
        }
        else if (str.contains("safe")&&str.contains("guard")){
            intent=asus("safe guard");
        }
        else if (str.contains("weather")){
            intent=asus("weather");
        }
        else if (str.contains("theme")){
            intent=asus("themes");
        }
        else if (str.contains("twin")&&str.contains("app")){
            intent=asus("twin apps");
        }
        else if ((str.contains("sound")||str.contains("voice"))&&str.contains("recorder")){
            intent=asus("sound recorder");
        }
        else if ((str.contains("selfie")&&str.contains("master"))||str.contains("camera")){
            intent=asus("selfie master");
        }
        else if ((str.contains("power")||str.contains("battery"))&&str.contains("saver")){
            intent=asus("power saver");
        }
        else if (str.contains("my")&&str.contains("asus")){
            intent=asus("my asus");
        }
        else if (str.contains("mobile")&&str.contains("manager")){
            intent=asus("mobile manager");
        }
        else if (str.contains("lock")&&str.contains("screen")){
            intent=asus("lock screen");
        }
        else if (str.contains("kids")&&str.contains("mode")){
            intent=asus("kids mode");
        }
        else if (str.contains("gallery")||str.contains("photos")){
            intent=asus("gallery");
        }
        else if (str.contains("game")&&str.contains("genie")){
            intent=asus("game genie");
        }
        else if (str.contains("file")&&str.contains("manager")){
            intent=asus("file manager");
        }
        else if (str.contains("easy")&&str.contains("mode")){
            intent=asus("easy mode");
        }
        else if (str.contains("contact")){
            intent=asus("contacts");
        }
        else if (str.contains("clock")){
            intent=asus("clock");
        }
    }
    private Intent asus(String str){
        String name="";
        switch (str){
            case "keyboard":{name="com.asus.ime";break;}
            case "safe guard":{name="com.asus.emergencyhelp";break;}
            case "weather":{name="com.asus.weathertime";break;}
            case "themes":{name="com.asus.themeapp";break;}
            case "twin apps":{name="com.asus.twinapps";break;}
            case "sound recorder":{name="com.asus.soundrecorder";break;}
            case "selfie master":{name="com.asus.selfiemaster";break;}
            case "power saver":{name="com.asus.powersaver";break;}
            case "my asus":{name="com.asus.ia.asusapp";break;}
            case "mobile manager":{name="com.asus.mobilemanager";break;}
            case "lock screen":{name="com.asus.lockscreen2";break;}
            case "kids mode":{name="com.asus.kidslauncher";break;}
            case "gallery":{name="com.asus.gallery";break;}
            case "game genie":{name="com.asus.gamewidget";break;}
            case "file manager":{name="com.asus.filemanager";break;}
            case "easy mode":{name="com.asus.easylauncher";break;}
            case "contacts":{name="com.asus.contacts";break;}
            case "clock":{name="com.asus.deskclock";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callXiaomi(String str) {
        if (str.contains("health")){
            intent=xiaomi("health");
        }
        else if (str.contains("calculator")){
            intent=xiaomi("calculator");
        }
        else if (str.contains("clean")&&str.contains("master")){
            intent=xiaomi("clean master");
        }
        else if (str.contains("compass")){
            intent=xiaomi("compass");
        }
        else if (str.contains("gallery")){
            intent=xiaomi("gallery");
        }
        else if (str.contains("notes")){
            intent=xiaomi("notes");
        }
        else if (str.contains("screen")&&str.contains("record")){
            intent=xiaomi("screen recoder");
        }
        else if (str.contains("weather")||str.contains("mousam")){
            intent=xiaomi("weather");
        }
        else if (str.contains("news")){
            intent=xiaomi("news");
        }
        else if (str.contains("video")&&str.contains("player")){
            intent=xiaomi("video player");
        }
        else if (str.contains("calendar")||str.contains("calender")||str.contains("date")){
            intent=xiaomi("calender");
        }
    }
    private Intent xiaomi(String str){
        String name="";
        switch (str){
            case "health":{name="com.mi.health";break;}
            case "calculator":{name="com.miui.calculator";break;}
            case "clean master":{name="com.miui.cleanmaster";break;}
            case "compass":{name="com.miui.compass";break;}
            case "gallery":{name="com.miui.gallery";break;}
            case "notes":{name="com.miui.notes";break;}
            case "screen recorder":{name="com.miui.screenrecorder";break;}
            case "weather":{name="com.miui.weather2";break;}
            case "news":{name="com.opera.app.news";break;}
            case "video player":{name="com.miui.videoplayer";break;}
            case "calender":{name="com.xiaomi.calendar";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callGoogle(String str) {
        if (str.contains("dial")){
            intent=google("dialer");
        }
        else if (str.contains("drive")&&str.contains("google")){
            intent=google("drive");
        }
        else if (str.contains("map")){
            intent=google("map");
        }
        else if (str.contains("photos")||str.contains("google photos")){
            intent=google("photos");
        }
        else if (str.contains("wallet")){
            intent=google("wallet");
        }
        else if (str.contains("calendar")||str.contains("calender")||str.contains("date")){
            intent=google("calender");
        }
        else if (str.contains("duo")){
            intent=google("duo");
        }
        else if (str.contains("gmail")){
            intent=google("gmail");
        }
        else if (str.contains("gboard")){
            intent=google("gboard");
        }
        else if (str.contains("talk")&&str.contains("back")){
            intent=google("talkback");
        }
        else if (str.contains("google music")||str.contains("music")){
            intent=google("music");
        }
        else if (str.contains("youtube")){
            intent=google("youtube");
        }
        else if (str.contains("movies")&&(str.contains("tv")||str.contains("google"))){
            intent=google("movies");
        }
        else if (str.contains("text to speech")){
            intent=google("tts");
        }
        else if (str.contains("google")||str.contains("google search")){
            intent=google("quick search");
        }
    }
    private Intent google(String str){
        String name="";
        switch (str){
            case "dialer":{name="com.google.android.dialer\n";break;}       // no
            case "drive":{name="com.google.android.apps.docs";break;}
            case "map":{name="com.google.android.apps.maps";break;}
            case "photos":{name="com.google.android.apps.photos";break;}
            case "wallet":{name="com.google.android.apps.walletnfcrel";break;}
            case "calender":{name="com.google.android.calendar";break;}
            case "duo":{name="com.google.android.apps.tachyon";break;}
            case "gmail":{name="com.google.android.gm";break;}
            case "gboard":{name="com.google.android.inputmethod.latin";break;}
            case "talkback":{name="com.google.android.marvin.talkback";break;}
            case "music":{name="com.google.android.music";break;}
            case "youtube":{name="com.google.android.youtube";break;}
            case "movies":{name="com.google.android.videos";break;}
            case "tts":{name="com.google.android.tts";break;}
            case "wallpaper":{name="com.google.android.apps.wallpaper";break;}
            case "quick search":{name="com.google.android.googlequicksearchbox";break;}
            case "wellbeing":{name="com.google.android.apps.wellbeing";break;}
            case "youtube music":{name="com.google.android.apps.youtube.music";break;}
            case "books":{name="com.google.android.apps.books";break;}
            case "cloud print":{name="com.google.android.apps.cloudprint";break;}
            case "currents":{name="com.google.android.apps.currents";break;}
            case "fit":{name="com.google.android.apps.fitness";break;}
            case "feed back":{name="com.google.android.feedback";break;}
            case "play games":{name="com.google.android.play.games";break;}
            case "street view":{name="com.google.android.street";break;}
            case "measure":{name="com.google.tango.measure";break;}

        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callAndroid(String str) {
        if (str.contains("playstore")) {
            intent = android("playstore");
        } else if (str.contains("ok") && str.contains("google")) {
            intent = android("ok google");
        } else if (str.contains("wall") && str.contains("paper")) {
            intent = android("wallpaper");
        } else if (str.contains("chrome") ) {
            intent = android("chrome");
        } else if (str.contains("easter") && str.contains("egg")) {
            intent = android("easter egg");
        } else if (str.contains("file manager")) {
            intent = android("files");
        } else if (str.contains("mms")) {
            intent = android("mms");
        } else if (str.contains("notes")) {
            intent = android("notes");
        } else if (str.contains("sim") && str.contains("kit")) {
            intent = android("sim tool kit");
        } else if (str.contains("video") && str.contains("player")) {
            intent = android("video player");
        } else if (str.contains("browser") && !str.contains("beta")) {
            intent = google("browser");
        } else if (str.contains("bluetooth")) {
            intent = google("bluetooth");
        } else if (str.contains("setting")) {
            intent = google("setting");
        } else if (str.contains("360") && str.contains("photo") && str.contains("edit")) {
            intent = android("360 photo editor");
        } else if (str.contains("optical") && str.contains("reader")) {
            intent = android("optical reader");
        } else if (str.contains("snap") && (str.contains("biz") || str.contains("business"))) {
            intent = android("snap biz");
        } else if (str.contains("apax") && str.contains("service")) {
            intent = android("apax service");
        } else if (str.contains("samsung") && (str.contains("checkout")) || str.contains("bill")) {
            intent = android("samsung checkout");
        } else if (str.contains("samsung") && str.contains("experience") && str.contains("home")) {
            intent = android("samsung experience home");
        } else if (str.contains("browser") && str.contains("beta")) {
            intent = android("browser beta");
        } else if (str.contains("kids") && str.contains("mode")) {
            intent = android("kids mode");
        } else if (str.contains("photo") && str.contains("screen") && str.contains("saver")) {
            intent = android("photo screensaver");
        } else if (str.contains("clock")) {
            intent = android("clock");
        } else if (str.contains("calendar")) {
            intent = android("calendar");
        } else if (str.contains("notepad")) {
            intent = android("notepad");
        } else if (str.contains("download")) {
            intent = android("download");
            if (intent == null)
                intent = android("downloads");
            if (intent == null)
                intent = android("downloads1");
        } else if (str.contains("calculator")) {
            intent = android("calculator");
        } else if (str.contains("contact")) {
            intent = android("contacts");
        } else if (str.contains("emergency") && str.contains("info")) {
            intent = android("emergency info");
        } else if (str.contains("group") && str.contains("music")) {
            intent = android("group music");
        } else if (str.contains("phone")) {
            intent = new Intent(Intent.ACTION_CALL_BUTTON);
        }

    }
    private Intent android(String str){
        String name="";
        switch (str){
            case "playstore":               {name="com.android.vending";break;}
            case "ok google":               {name="com.android.hotwordenrollment.okgoogle";break;}
            case "wallpaper":               {name="com.sec.android.wallapapercropper2";break;}
            case "chrome":                  {name="com.android.chrome";break;}
            case "easter egg":              {name="com.android.egg";break;}
            case "files":                   {name="com.android.filemanager";break;}
            case "mms":                     {name="com.android.mms.service";break;}
            case "notes":                   {name="com.android.notes";break;}
            case "sim tool kit":            {name="com.android.stk";break;}
            case "video player":            {name="com.android.VideoPlayer";break;}
            case "browser":                 {name="com.android.browser";break;}
            case "bluetooth":               {name="com.android.bluetooth";break;}
            case "setting":                 {name="com.android.settings";break;}
            case "download":                {name="com.android.providers.downloads";break;}
            case "downloads":               {name="com.android.providers.downloads.ui";break;}
            case "downloads1":              {name="com.android.documentsui";break;}
            case "media":                   {name="com.android.providers.media";break;}
            case "settings":                {name="com.android.providers.settings";break;}
            case "sms":                     {name="com.android.mms";break;}
            case "call recorder":           {name="com.android.phone.recorder";break;}
            case "sound recorder":          {name="com.android.soundrecorder";break;}
            case "video editor":            {name="com.sec.android.app.vepreload";break;}
            case "360 photo editor":        {name="com.sec.android.mimage.gear360editor";break;}
            case "optical reader":          {name="com.sec.android.app.ocr4";break;}
            case "snap biz":                {name="com.sec.android.app.bcocr";break;}
            case "apx service":             {name="com.sec.android.app.apex";break;}
            case "samsung checkout":        {name="com.sec.android.app.billing";break;}
            case "samsung experience home": {name="com.sec.android.app.launcher";break;}
            case "browser beta":            {name="com.sec.android.app.sbrowser.beta";break;}
            case "kids mode":               {name="com.sec.android.app.kidshome";break;}
            case "photo screensaver":       {name="com.android.dreams.phototable";break;}
            case "clock":                   {name="com.android.deskclock";break;}
            case "calendar":                {name="com.android.calendar";break;}
            case "notepad":                 {name="com.example.android.notepad";break;}
            case "calculator":              {name="com.android.calculator2";break;}
            case "contacts":                {name="com.android.contacts";break;}
            case "emergency info":          {name="com.android.emergency";break;}
            case "group music":             {name="com.android.imedia.syncplay";break;}
            case "":        {name="";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callNokia(String str) {
        if (str.contains("camera")){
            intent=nokia("camera");
        }
        else if (str.contains("fm")||str.contains("radio")){
            intent=nokia("fm");
        }
        else{intent=null;}
    }
    private Intent nokia(String str){
        String name="";
        switch (str){
            case "camera":{name="com.evenwell.camera2";break;}
            case "fm":{name="com.evenwell.fmradio";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callHuawei(String str) {
        if (str.contains("pay")){
            intent=huawei("pay");
        }
        else if (str.contains("weather")){
            intent=huawei("weather");
        }
        else if (str.contains("browser")){
            intent=huawei("browser");
        }
        else if (str.contains("compass")){
            intent=huawei("compass");
        }
        else if (str.contains("chinese")&&str.contains("keyboard")){
            intent=huawei("chinese keyboard");
        }
        else if (str.contains("app")&&str.contains("gallery")){
            intent=huawei("app market");
        }
        else if (str.contains("system")&&str.contains("search")){
            intent=huawei("system search");
        }
        else if (str.contains("business")&&str.contains("card")){
            intent=huawei("business card reader");
        }
        else if (str.contains("music")&&!str.contains("google")&&!str.contains("group")){
            intent=huawei("music");
        }
        else if (str.contains("screen")&&str.contains("record")){
            intent=huawei("screen record");
        }
        else if (str.contains("voice")&&str.contains("assistant")){
            intent=huawei("voice assistant");
        }
        else if (str.contains("video")&&str.contains("editor")){
            intent=huawei("video editor");
        }
        else if (str.contains("video")){
            intent=huawei("video");
        }
        else if (str.contains("wallet")){
            intent=huawei("wallet");
        }
        else if (str.contains("theme")){
            intent=huawei("theme");
        }
        else if (str.contains("file")){
            intent=huawei("files");
        }
        else if (str.contains("cloud")){
            intent=huawei("cloud");
        }
        else if (str.contains("camera")){
            intent=huawei("camera");
        }
        else if (str.contains("fm")||str.contains("radio")){
            intent=huawei("fm");
        }
        else if (str.contains("tips")){
            intent=huawei("tips");
        }
        else{callHeytap(str);}
    }
    private Intent huawei(String str){
        String name="";
        switch (str){
            case "chinese keyboard":{name="com.baidu.input_huawei";break;}  //// no
            case "pay":{name="com.huawei.android.hwpay";break;} //// no
            case "app market":{name="com.huawei.appmarket";break;}
            case "system search":{name="com.huawei.search";break;}
            case "weather":{name="com.huawei.android.totemweatherapp";break;}
            case "browser":{name="com.huawei.browser";break;}   //// no
            case "compass":{name="com.huawei.compass";break;}
            case "business card reader":{name="com.huawei.contactscamcard";break;}  //// no
            case "video":{name="com.huawei.himovie";break;}
            case "screen record":{name="com.h   uawei.screenrecorder";break;}  //// no
            case "voice assistant":{name="com.huawei.vassistant";break;}    //// no
            case "video editor":{name="com.huawei.videoeditor";break;}  //// no
            case "wallet":{name="com.huawei.wallet";break;} //// no
            case "music":{name="com.android.mediacenter";break;}
            case "theme":{name="com.huawei.android.thememanager";break;}
            case "cloud":{name="com.huawei.hidisk";break;}
            case "camera":{name="com.huawei.camera";break;}
            case "fm":{name="com.huawei.android.FMRadio";break;}
            case "tips":{name="com.huawei.android.tips";break;}
            case "":{name="";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callHeytap(String str) {
        if (str.contains("cloud")){
            intent=heytap("cloud");
        }
        else if (str.contains("browser")){
            intent=heytap("browser");
        }
        else if (str.contains("app")&&str.contains("market")){
            intent=heytap("app market");
        }
        else if (str.contains("system")&&str.contains("message")){
            intent=heytap("system messages");
        }
        else{callColoros(str);}
    }
    private Intent heytap(String str){
        String name="";
        switch (str){
            case "cloud":{name="com.heytap.cloud";break;}
            case "browser":{name="com.heytap.browser";break;}
            case "app market":{name="com.heytap.market";break;}
            case "system messages":{name="com.heytap.mcs";break;}
            case "":{name="";break;}

        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());

            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private void callColoros(String str) {
        if (str.contains("compass")){
            intent=coloros("compass");
        }
        else if (str.contains("gallery")||str.contains("pics")){
            intent=coloros("gallery");
        }
        else{intent=null;}
    }
    private Intent coloros(String str){
        String name="";
        switch (str){
            case "compass":{name="com.coloros.compass2";break;}
            case "gallery":{name="com.coloros.gallery3d";break;}
        }
        if( pk.getLaunchIntentForPackage(name)!=null){
            try {
                txtBack+=(String) pk.getApplicationLabel(pk.getApplicationInfo(name,PackageManager.GET_META_DATA));
                packName=name;
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Open.class.getName());
            }
        }
        return pk.getLaunchIntentForPackage(name);
    }

    private String notFound() { 
        return "App Not Found";
    }
    public String packageNme(String str){
        setData(str);
        return packName;
    }
}
