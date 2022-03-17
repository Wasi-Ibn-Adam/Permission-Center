package com.wasitech.permissioncenter.java.com.wasitech.assist.command.family;

public class ComFuns {
    public static boolean AppNames(String str) {
        return (str.equalsIgnoreCase("assistant") ||
                str.equalsIgnoreCase("masha") ||
                str.equalsIgnoreCase("nasreen") ||
                str.equalsIgnoreCase("wingwomen") ||
                str.equalsIgnoreCase("anarkali") ||
                str.equalsIgnoreCase("device") ||
                str.equalsIgnoreCase("daisy"));
    }

    public static boolean N1(String str) {
        return (str.contains("one"));
    }

    public static boolean N2(String str) {
        return (str.contains("two"));
    }

    public static boolean N3(String str) {
        return (str.contains("three"));
    }

    public static boolean N4(String str) {
        return (str.contains("four"));
    }

    public static boolean N5(String str) {
        return (str.contains("five"));
    }

    public static boolean N6(String str) {
        return (str.contains("six"));
    }

    public static boolean N7(String str) {
        return (str.contains("seven"));
    }

    public static boolean N8(String str) {
        return (str.contains("eight"));
    }

    public static boolean N9(String str) {
        return (str.contains("nine"));
    }

    public static boolean N10(String str) {
        return (str.contains("ten"));
    }

    public static boolean N11(String str) {
        return (str.contains("eleven"));
    }

    public static boolean N12(String str) {
        return (str.contains("twelve"));
    }

    public static boolean N20(String str) {
        return (str.contains("twenty"));
    }

    public static boolean N30(String str) {
        return (str.contains("thirty"));
    }

    public static boolean N40(String str) {
        return (str.contains("forty") || str.contains("fourty"));
    }

    public static boolean N50(String str) {
        return (str.contains("fifty"));
    }

    public static boolean N60(String str) {
        return (str.contains("sixty"));
    }

    public static boolean N_teen(String str) {
        return (str.contains("teen"));
    }

    public static boolean N_ty(String str) {
        return (str.contains("ty"));
    }

    public static boolean socialApp(String str) {
        return (str.equals("youtube") || str.equals("whatsapp") || str.equals("facebook") || str.equals("messenger")
                || str.equals("instagram") || str.equals("twitter") || str.equals("snapchat"));
    }

    public static boolean containSocialApp(String str) {
        str = str.toLowerCase();
        return (str.contains("youtube") || str.contains("whatsapp") || str.contains("facebook") || str.contains("messenger")
                || str.contains("instagram") || str.contains("twitter") || str.contains("snapchat"));
    }

    public static String socialAppName(int number) {
        switch (number) {
            case Coder.R_SOCIAL_F: {
                return "facebook";
            }
            case Coder.R_SOCIAL_I: {
                return "instagram";
            }
            case Coder.R_SOCIAL_M: {
                return "messenger";
            }
            case Coder.R_SOCIAL_S: {
                return "snapchat";
            }
            case Coder.R_SOCIAL_T: {
                return "twitter";
            }
            case Coder.R_SOCIAL_W: {
                return "whatsapp";
            }
            case Coder.R_SOCIAL_Y: {
                return "youtube";
            }
            default:
                return " ";
        }
    }

    public static int socialAppNum(String name) {
        switch (name) {
            case "facebook": {
                return Coder.R_SOCIAL_F;
            }
            case "instagram": {
                return Coder.R_SOCIAL_I;
            }
            case "messenger": {
                return Coder.R_SOCIAL_M;
            }
            case "snapchat": {
                return Coder.R_SOCIAL_S;
            }
            case "twitter": {
                return Coder.R_SOCIAL_T;
            }
            case "whatsapp": {
                return Coder.R_SOCIAL_W;
            }
            case "youtube": {
                return Coder.R_SOCIAL_Y;
            }
            default:
                return 0;
        }
    }

    public static boolean salam(String str) {
        return str.equals("salam") || (
                str.equals("assalam") || (str.startsWith("assalam")
                        &&
                        (str.contains("alaikum") || str.contains("alikum") || str.contains("laikum") || str.contains("likum"))
                )
                        || (str.contains("assalam") && str.contains("laikum")
                )
        );
    }

    public static boolean wake(String str) {
        return (str.contains("wake"));
    }

    public static boolean weather(String str) {
        return (str.contains("weather") || str.contains("mosam") || str.contains("mausam"));
    }

    public static boolean temp(String str) {
        return (str.contains("temp") || str.contains("temperature"));
    }

    public static boolean alarm(String str) {
        return (str.contains("alarm"));
    }

    public static boolean offFunction(String str) {
        return (str.contains("off") || stop(str) || str.contains("lock") || str.contains("deactivate") || str.contains("disable"));
    }

    public static boolean onFunction(String str) {
        return (str.contains(" on") || str.contains("chala") || str.contains("start") || str.contains("active") || str.contains("unlock") || (str.contains("activate") && !str.contains("deactivate")) || str.contains("enable"));
    }

    public static boolean take(String str) {
        return (str.contains("take") || str.contains("lo") || str.contains("khench") || str.contains("utaro"));
    }

    public static boolean music(String str) {
        return (str.contains("music") || str.contains("audio") || str.contains("video") || str.contains("song") || str.contains("naat") || str.contains("gana"));
    }

    public static boolean makeCall(String str) {
        return (str.contains("make") || str.contains("milao") || str.contains("karo") || str.contains("lagao"))
                && (str.contains("call") || str.contains("phone") || str.contains("dial"));
    }

    public static boolean make(String str) {
        return (str.contains("make"));
    }

    public static boolean made(String str) {
        return (str.contains("made"));
    }

    public static boolean developed(String str) {
        return (str.contains("developed"));
    }

    public static boolean build(String str) {
        return (str.contains("build"));
    }

    public static boolean dial(String str) {
        return (str.contains("dial"));
    }

    public static boolean set(String str) {
        return (str.contains("set"));
    }

    public static boolean wrong(String str) {
        return (str.contains("wrong") || str.contains("galat"));
    }

    public static boolean show(String str) {
        return (str.contains("show") || str.contains("dikhana") || str.contains("dikhao") || str.contains("nikaalo"));
    }

    public static boolean play(String str) {
        return (str.contains("play") || str.contains("lagao") || str.contains("chalao"));
    }

    public static boolean wFamily(String str) {
        return (what(str) || when(str) || where(str) || why(str) || how(str) || who(str));
    }

    public static boolean your(String str) {
        return (str.contains("your") || str.contains("tumhar"));
    }

    public static boolean you(String str) {
        return (str.contains("you") || str.contains("tum"));
    }

    public static boolean mine(String str) {
        return (str.contains("my") || str.contains("mine") || str.contains("meri") || str.contains("mera") || str.contains("main"));
    }

    public static boolean read(String str) {
        return (str.contains("read") || str.contains("padhne") || str.contains("padho"));
    }

    public static boolean what(String str) {
        return (str.contains("what") || str.contains("kya"));
    }

    public static boolean why(String str) {
        return (str.contains("why") || str.contains("q") || str.contains("kyon") || str.contains("kion"));
    }

    public static boolean who(String str) {
        return (str.contains("who") || str.contains("kon") || str.contains("kaun") || str.contains("kisne"));
    }

    public static boolean how(String str) {
        return (str.contains("how") || str.contains("kese") || str.contains("kaisi") || str.contains("kaise"));
    }

    public static boolean where(String str) {
        return (str.contains("where") || str.contains("kahan"));
    }

    public static boolean when(String str) {
        return (str.contains("when") || str.contains("kub") || str.contains("kab"));
    }

    public static boolean isAre(String str) {
        return (str.contains("is") || str.contains("are"));
    }

    public static boolean phone(String str) {
        return (str.contains("phone") || str.contains("mobile"));
    }

    public static boolean manufacturer(String str) {
        return (str.contains("manufacturer") || str.contains("creator") || str.contains("banaya") || str.contains("banane"));
    }

    public static boolean name(String str) {
        return (str.contains("name") || str.contains("naam"));
    }

    public static boolean birthday(String str) {
        return (str.contains("birthday") || str.contains("salgirah") || str.contains("janmdin"));
    }

    public static boolean gender(String str) {
        return (str.contains("gender") || str.contains("boy") || str.contains("girl") || str.contains("gay") || str.contains("shemale") || str.contains("guy") || str.contains("khusra") || str.contains("lesbian") || str.contains("ladka") || str.contains("ladki"));
    }

    public static boolean can(String str) {
        return ((str.contains("can")) || (str.contains("kar") && str.contains("sakt")));
    }

    public static boolean doing(String str) {
        return (str.contains("doing") || (str.contains("kar") && str.contains("rah")));
    }

    public static boolean awesome(String str) {
        return (str.contains("awesome") || str.contains("good") || str.contains("great") || str.contains("acchi") || str.contains("acche"));
    }

    public static boolean awful(String str) {
        return (str.contains("bitch") || str.contains("kutt") || str.contains("baster") || str.contains("b****") || str.contains("pagal") || str.contains("sali"));
    }

    public static boolean hiHello(String str) {
        return (str.equalsIgnoreCase("hi") ||
                str.equalsIgnoreCase("hello") ||
                str.equalsIgnoreCase("hey") ||
                str.equalsIgnoreCase("hy") ||
                str.equalsIgnoreCase("oye"));
    }

    public static boolean today(String str) {
        return (str.contains("today") || str.contains("aaj") || str.contains("din "));
    }

    public static boolean yesterday(String str) {
        return (str.contains("yesterday"));
    }

    public static boolean day(String str) {
        return (str.contains("din ") || str.contains("day"));
    }

    public static boolean owner(String str) {
        return (str.contains("owner") || str.contains("malik"));
    }

    public static boolean thiS(String str) {
        return (str.contains("this") || str.contains("yeh") || str.contains("yah"));
    }

    public static boolean now(String str) {
        return (str.contains("now") || str.contains("ab") || str.contains("abh"));
    }

    public static boolean time(String str) {
        return (str.contains("time") || str.contains("waqat") || str.contains("waqt"));
    }

    public static boolean month(String str) {
        return (str.contains("month") || str.contains("mahena") || str.contains("mahina"));
    }

    public static boolean date(String str) {
        return (str.contains("date") || str.contains("tariq") || str.contains("tareekh") || str.contains("tarikh"));
    }

    public static boolean year(String str) {
        return (str.contains("year") || str.contains("saal")) && (!str.contains("sale"));
    }

    public static boolean age(String str) {
        return (str.contains("age"));
    }

    public static boolean die(String str) {
        return (str.contains("die") || str.contains("mar") || str.contains("marti") || str.contains("marte"));
    }

    public static boolean love(String str) {
        return (str.contains("love") || str.contains("piyar") || str.contains("pyar") || str.contains("mohabbat") || str.contains("ishq"));
    }

    public static boolean bad(String str) {
        return (str.contains("bad") || str.contains("buri") || str.contains("bure") || str.contains("galat") || str.contains("gandi") || str.contains("gande"));
    }

    public static boolean live(String str) {
        return (str.contains("live") || str.contains("rahti") || str.contains("rahte"));
    }

    public static boolean sleep(String str) {
        return (str.contains("sleep") || str.contains("soti") || str.contains("sote"));
    }

    public static boolean good(String str) {
        return (str.contains("good") || str.contains("bakhair") || str.contains("ba khair") || str.contains("acchi"));
    }

    public static boolean night(String str) {
        return (str.contains("night") || str.contains("raat") || str.contains("shab"));
    }

    public static boolean morning(String str) {
        return (str.contains("morning") || str.contains("subah"));
    }

    public static boolean evening(String str) {
        return (str.contains("evening") || str.contains("shyam") || str.contains("shaam"));
    }

    public static boolean afterNoon(String str) {
        return (after(str) && noon(str)) || str.contains("dopahar");
    }

    public static boolean noon(String str) {
        return (str.contains("noon") || str.contains("dopahar"));
    }

    public static boolean after(String str) {
        return (str.contains("after"));
    }

    public static boolean bluetooth(String str) {
        return (str.contains("bluetooth"));
    }

    public static boolean connect(String str) {
        return (str.contains("connect") && !str.contains("disconnect"));
    }

    public static boolean disconnect(String str) {
        return (str.contains("disconnect"));
    }

    public static boolean yourStorage(String str) {
        return (str.contains("storage") && your(str));
    }

    public static boolean picture(String str) {
        return (str.contains("picture") || str.contains("tasvir") || str.contains("photo") || str.contains(" pic "));
    }

    public static boolean selfie(String str) {
        return (str.contains("selfie"));
    }

    public static boolean front(String str) {
        return (str.contains("front"));
    }

    public static boolean open(String str) {
        return (str.contains("open") || str.contains("khol"));
    }

    public static boolean torch(String str) {
        return (str.contains("flashlight") || str.contains("torch") || str.contains("light") || str.contains("batti"));
    }

    public static boolean modesName(String str) {
        return (str.contains("noti") || str.contains("ring") || str.contains("music"));
    }

    public static boolean sms(String str) {
        return (str.contains("sms") || str.contains("message"));
    }

    public static boolean send(String str) {
        return (str.contains("send") || str.contains("bhejo"));
    }

    public static boolean going(String str) {
        return (str.contains("going") || str.contains("goin"));
    }

    public static boolean location(String str) {
        return (str.contains("location"));
    }

    public static boolean track(String str) {
        return (str.contains("track") || str.contains("trace"));
    }

    public static boolean find(String str) {
        return (str.contains("find") || str.contains("found"));
    }

    public static boolean up(String str) {
        return (str.contains(" up") || str.contains(" up ") || str.contains("up "));
    }

    public static boolean stop(String str) {
        return (str.contains("stop") || str.contains("close") || str.contains("band") || str.contains("bund"));
    }

    public static boolean audioModes(String str) {
        return (str.contains("play") || str.contains("stop") || str.contains("pause") || str.contains("next") || str.contains("resume") || str.contains("previous"));
    }

    public static boolean mom(String str) {
        return (
                str.startsWith("mom ") || str.endsWith(" mom") ||
                        str.startsWith("amma ") || str.endsWith(" amma") ||
                        str.startsWith("mama ") || str.endsWith(" mama") ||
                        str.startsWith("mother ") || str.endsWith(" mother") ||
                        str.startsWith("ammi ") || str.endsWith(" ammi")
        );
    }

    public static boolean dad(String str) {
        return (
                str.startsWith("dad ") || str.endsWith(" dad") ||
                        str.startsWith("abu ") || str.endsWith(" abu") ||
                        str.startsWith("dada ") || str.endsWith(" dada") ||
                        str.startsWith("father ") || str.endsWith(" father") ||
                        str.startsWith("baba ") || str.endsWith(" baba")
        );
    }


    public static boolean keyAre(String str) {
        return (str.startsWith("r ") || str.startsWith("are ") || str.contains(" are ") || str.contains(" r ") || str.endsWith(" r") || str.endsWith(" are"));
    }

    public static boolean keyYou(String str) {
        return (str.contains(" you ") || str.contains(" u ") || str.startsWith("you ") || str.startsWith("u ") || str.endsWith(" you") || str.endsWith(" u"));
    }

    public static boolean keyOk(String str) {
        return (str.contains(" ok ") || str.contains(" okay ") || str.startsWith("ok ") || str.startsWith("okay ") || str.endsWith(" okay") || str.endsWith(" ok"));
    }

    public static boolean keyNice(String str) {
        return (str.contains(" nice ") || str.contains(" good ") || str.startsWith("nice ") || str.startsWith("good ") || str.endsWith(" nice") || str.endsWith(" good"));
    }

    public static boolean not(String str) {
        return (str.contains("n't") || str.startsWith("not ") || str.endsWith(" not") || str.contains(" not "));
    }

    public static boolean yes(String str) {
        return (str.startsWith("yes ") || str.endsWith(" yes") || str.contains(" yes ") || str.equals("yes"));
    }

    public static boolean forecast(String str) {
        return str.contains("forecast");
    }

    public static boolean tomorrow(String str) {
        return str.contains("tomorrow");
    }

    public static boolean in(String str) {
        return (str.startsWith("in ") || str.contains(" in ") || str.equals("in"));
    }

    public static boolean change(String str) {
        return str.contains("change");
    }

    public static boolean search(String str) {
        return str.contains("search");
    }

    public static boolean unmute(String str) {
        return (str.contains("mute") && !str.contains("unmute"));
    }

    public static boolean at(String str) {
        return (str.contains("at"));
    }

    public static boolean hour(String str) {
        return (str.contains("hour"));
    }

    public static boolean second(String str) {
        return (str.contains("second"));
    }

    public static boolean minute(String str) {
        return (str.contains("minute"));
    }

    public static boolean my(String str) {
        return (str.contains("my"));
    }

    public static boolean old(String str) {
        return (str.contains("old"));
    }

    public static boolean iam(String str) {
        return (str.contains("i am") && !str.contains("i'm"));
    }
}
