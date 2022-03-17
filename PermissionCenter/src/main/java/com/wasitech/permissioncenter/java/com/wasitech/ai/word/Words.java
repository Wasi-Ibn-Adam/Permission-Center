package com.wasitech.permissioncenter.java.com.wasitech.ai.word;

import com.wasitech.basics.classes.Basics;

public class Words {
    public Words(String str) {
        boolean a = Es.E_s(str)
                || Fs.F_s(str)
                || Gs.G_s(str)
                || Hs.H_s(str)
                || Is.I_s(str)
                || Js.J_s(str)
                || Ks.K_s(str)
                || Ls.L_s(str)
                || Ms.M_s(str)
                || Ns.N_s(str)
                || Os.O_s(str)
                || Ps.P_s(str)
                || Qs.Q_s(str)
                || Rs.R_s(str)
                || Ss.S_s(str)
                || Ts.T_s(str)
                || Us.U_s(str)
                || Vs.V_s(str)
                || Ws.W_s(str)
                || Xs.X_s(str)
                || Ys.Y_s(str)
                || Zs.Z_s(str);

        Basics.Log("Initial with ABC " + a);
    }




    public static class Es {
        public static boolean E_s(String str) {
            return evening(str) && enable(str);
        }

        public static boolean enable(String str) {
            return str.contains("enable");
        }

        public static boolean evening(String str) {
            return str.contains("evening");
        }
    }

    public static class Fs {
        public static boolean F_s(String str) {
            return forecast(str) &&
                    find(str) &&
                    found(str) &&
                    front(str) &&
                    flash(str) &&
                    flashlight(str);
        }

        public static boolean forecast(String str) {
            return str.contains("forecast");
        }

        public static boolean find(String str) {
            return str.contains("find");
        }

        public static boolean found(String str) {
            return str.contains("found");
        }

        public static boolean front(String str) {
            return str.contains("front");
        }

        public static boolean flash(String str) {
            return str.contains("flash");
        }

        public static boolean flashlight(String str) {
            return str.contains("flashlight");
        }

    }

    public static class Gs {
        public static boolean G_s(String str) {
            return girl(str) &&
                    gay(str) &&
                    guy(str) &&
                    gender(str) &&
                    good(str) &&
                    going(str) &&
                    go(str);
        }

        public static boolean girl(String str) {
            return str.contains("girl");
        }

        public static boolean gay(String str) {
            return str.contains("gay");
        }

        public static boolean guy(String str) {
            return str.contains("guy");
        }

        public static boolean gender(String str) {
            return str.contains("gender");
        }

        public static boolean good(String str) {
            return str.contains("good");
        }

        public static boolean going(String str) {
            return str.contains("going");
        }

        public static boolean go(String str) {
            return str.contains("go");
        }
    }

    public static class Hs {
        public static boolean H_s(String str) {
            return hi(str) &&
                    hello(str) &&
                    hey(str) &&
                    hy(str) &&
                    hour(str) &&
                    how(str);
        }

        public static boolean hi(String str) {
            return str.contains("hi");
        }

        public static boolean hello(String str) {
            return str.contains("hello");
        }

        public static boolean hey(String str) {
            return str.contains("hey");
        }

        public static boolean hy(String str) {
            return str.contains("hy");
        }

        public static boolean hour(String str) {
            return str.contains("hour");
        }

        public static boolean how(String str) {
            return str.contains("how");
        }

    }

    public static class Is {
        public static boolean I_s(String str) {
            return in(str) &&
                    i_am(str) &&
                    is(str);
        }

        public static boolean in(String str) {
            return str.contains("in");
        }

        public static boolean i_am(String str) {
            return (str.contains("i am") && !str.contains("i'm"));
        }

        public static boolean is(String str) {
            return str.contains("is");
        }

    }

    public static class Js {
        public static boolean J_s(String str) {
            return job(str);
        }

        public static boolean job(String str) {
            return str.contains("job");
        }
    }

    public static class Ks {
        public static boolean K_s(String str) {
            return king(str);
        }

        public static boolean king(String str) {
            return str.contains("king");
        }

    }

    public static class Ls {
        public static boolean L_s(String str) {
            return location(str) &&
                    live(str) &&
                    love(str) &&
                    lesbian(str) &&
                    lock(str) &&
                    light(str);
        }

        public static boolean location(String str) {
            return str.contains("location");
        }

        public static boolean live(String str) {
            return str.contains("live");
        }

        public static boolean love(String str) {
            return str.contains("love");
        }

        public static boolean lesbian(String str) {
            return str.contains("lesbian");
        }

        public static boolean lock(String str) {
            return str.contains("lock");
        }

        public static boolean light(String str) {
            return str.contains("light");
        }

    }

    public static class Ms {
        public static boolean M_s(String str) {
            return music(str) &&
                    make(str) &&
                    makeCall(str) &&
                    made(str) &&
                    mine(str) &&
                    minute(str) &&
                    my(str) &&
                    manufacturer(str) &&
                    mobile(str) &&
                    morning(str) &&
                    mom(str) &&
                    mama(str) &&
                    mother(str) &&
                    message(str) &&
                    month(str);
        }

        public static boolean music(String str) {
            return str.contains("music");
        }

        public static boolean makeCall(String str) {
            return str.contains("make") && str.contains("call");
        }

        public static boolean make(String str) {
            return (str.contains("make"));
        }

        public static boolean made(String str) {
            return (str.contains("made"));
        }

        public static boolean mine(String str) {
            return str.contains("mine");
        }

        public static boolean minute(String str) {
            return (str.contains("minute"));
        }

        public static boolean my(String str) {
            return (str.contains("my"));
        }

        public static boolean manufacturer(String str) {
            return str.contains("manufacturer");
        }

        public static boolean mobile(String str) {
            return str.contains("mobile");
        }

        public static boolean morning(String str) {
            return str.contains("morning");
        }

        public static boolean mom(String str) {
            return str.contains("mom");
        }

        public static boolean mama(String str) {
            return str.contains("mama");
        }

        public static boolean mother(String str) {
            return str.contains("mother");
        }

        public static boolean message(String str) {
            return str.contains("message");
        }

        public static boolean month(String str) {
            return str.contains("month");
        }
    }

    public static class Ns {
        public static boolean N_s(String str) {
            return name(str) &&
                    now(str) &&
                    notification(str) &&
                    noon(str) &&
                    nice(str) &&
                    not(str) &&
                    night(str);
        }

        public static boolean name(String str) {
            return (str.contains("name") || str.contains("naam"));
        }

        public static boolean now(String str) {
            return str.contains("now");
        }

        public static boolean notification(String str) {
            return str.contains("notification");
        }

        public static boolean noon(String str) {
            return str.contains("noon");
        }

        public static boolean nice(String str) {
            return str.contains("nice");
        }

        public static boolean not(String str) {
            return str.contains("not");
        }

        public static boolean night(String str) {
            return str.contains("night");
        }
    }

    public static class Os {
        public static boolean O_s(String str) {
            return open(str) &&
                    owner(str) &&
                    off(str) &&
                    of(str) &&
                    on(str) &&
                    old(str) &&
                    ok(str);
        }

        public static boolean open(String str) {
            return str.contains("open");
        }

        public static boolean owner(String str) {
            return str.contains("owner");
        }

        public static boolean off(String str) {
            return str.contains("off");
        }

        public static boolean of(String str) {
            return str.contains("of");
        }

        public static boolean on(String str) {
            return str.contains("on");
        }

        public static boolean old(String str) {
            return str.contains("old");
        }

        public static boolean ok(String str) {
            return str.contains("ok");
        }
    }

    public static class Ps {
        public static boolean P_s(String str) {
            return picture(str) &&
                    photo(str) &&
                    pic(str) &&
                    phone(str) &&
                    play(str);
        }

        public static boolean picture(String str) {
            return str.contains("picture");
        }

        public static boolean photo(String str) {
            return str.contains("photo");
        }

        public static boolean pic(String str) {
            return str.contains("pic");
        }

        public static boolean phone(String str) {
            return str.contains("phone");
        }

        public static boolean play(String str) {
            return str.contains("play");
        }
    }

    public static class Qs {
        public static boolean Q_s(String str) {
            return queen(str);
        }

        public static boolean queen(String str) {
            return str.contains("queen");
        }
    }

    public static class Rs {
        public static boolean R_s(String str) {
            return read(str) && ring(str);
        }

        public static boolean read(String str) {
            return str.contains("read");
        }

        public static boolean ring(String str) {
            return str.contains("ring");
        }
    }

    public static class Ss {
        public static boolean S_s(String str) {
            return set(str) &&
                    show(str) &&
                    start(str) &&
                    shemale(str) &&
                    sleep(str) &&
                    storage(str) &&
                    sms(str) &&
                    send(str) &&
                    stop(str) &&
                    second(str) &&
                    search(str) &&
                    selfie(str) &&
                    sale(str) &&
                    song(str);
        }

        public static boolean set(String str) {
            return str.contains("set");
        }

        public static boolean show(String str) {
            return str.contains("show");
        }

        public static boolean start(String str) {
            return str.contains("start");
        }

        public static boolean shemale(String str) {
            return str.contains("shemale");
        }

        public static boolean sleep(String str) {
            return str.contains("sleep");
        }

        public static boolean storage(String str) {
            return str.contains("storage");
        }

        public static boolean sms(String str) {
            return str.contains("sms");
        }

        public static boolean send(String str) {
            return str.contains("send");
        }

        public static boolean stop(String str) {
            return str.contains("stop");
        }

        public static boolean second(String str) {
            return str.contains("second");
        }

        public static boolean search(String str) {
            return str.contains("search");
        }

        public static boolean selfie(String str) {
            return str.contains("selfie");
        }

        public static boolean sale(String str) {
            return str.contains("sale");
        }

        public static boolean song(String str) {
            return str.contains("song");
        }
    }

    public static class Ts {
        public static boolean T_s(String str) {
            return temp(str) &&
                    temperature(str) &&
                    take(str) &&
                    track(str) &&
                    trace(str) &&
                    tomorrow(str) &&
                    torch(str) &&
                    today(str) &&
                    time(str) &&
                    thiS(str);
        }

        public static boolean temp(String str) {
            return str.contains("temp");
        }

        public static boolean temperature(String str) {
            return str.contains("temperature");
        }

        public static boolean take(String str) {
            return str.contains("take");
        }

        public static boolean track(String str) {
            return str.contains("track");
        }

        public static boolean trace(String str) {
            return str.contains("trace");
        }

        public static boolean tomorrow(String str) {
            return str.contains("tomorrow");
        }

        public static boolean torch(String str) {
            return str.contains("torch");
        }

        public static boolean today(String str) {
            return str.contains("today");
        }

        public static boolean time(String str) {
            return str.contains("time");
        }

        public static boolean thiS(String str) {
            return str.contains("this");
        }
    }

    public static class Us {
        public static boolean U_s(String str) {
            return up(str) && unmute(str) && unlock(str);
        }

        public static boolean up(String str) {
            return str.contains("up");
        }

        public static boolean unmute(String str) {
            return str.contains("unmute");
        }

        public static boolean unlock(String str) {
            return str.contains("unlock");
        }


    }

    public static class Vs {
        public static boolean V_s(String str) {
            return valley(str);
        }

        public static boolean valley(String str) {
            return str.contains("valley");
        }

    }

    public static class Ws {
        public static boolean W_s(String str) {
            return wake(str) &&
                    weather(str) &&
                    wrong(str) &&
                    what(str) &&
                    why(str) &&
                    who(str) &&
                    where(str) &&
                    when(str) &&
                    write(str);
        }

        public static boolean wake(String str) {
            return (str.contains("wake"));
        }

        public static boolean weather(String str) {
            return str.contains("weather");
        }

        public static boolean wrong(String str) {
            return str.contains("wrong");
        }

        public static boolean what(String str) {
            return str.contains("what");
        }

        public static boolean why(String str) {
            return str.contains("why");
        }

        public static boolean who(String str) {
            return str.contains("who");
        }

        public static boolean where(String str) {
            return str.contains("where");
        }

        public static boolean when(String str) {
            return str.contains("when");
        }

        public static boolean write(String str) {
            return str.contains("write");
        }
    }

    public static class Xs {
        public static boolean X_s(String str) {
            return xray(str);
        }

        public static boolean xray(String str) {
            return str.contains("xray") || str.contains("x-ray");
        }

    }

    public static class Ys {
        public static boolean Y_s(String str) {
            return your(str) &&
                    you(str) &&
                    yesterday(str) &&
                    yes(str) &&
                    year(str);
        }

        public static boolean your(String str) {
            return str.contains("your");
        }

        public static boolean you(String str) {
            return str.contains("you");
        }

        public static boolean yesterday(String str) {
            return str.contains("yesterday");
        }

        public static boolean yes(String str) {
            return str.contains("yes");
        }

        public static boolean year(String str) {
            return str.contains("year");
        }
    }

    public static class Zs {
        public static boolean Z_s(String str) {
            return zoo(str);
        }

        public static boolean zoo(String str) {
            return str.contains("zoo");
        }

    }
}
