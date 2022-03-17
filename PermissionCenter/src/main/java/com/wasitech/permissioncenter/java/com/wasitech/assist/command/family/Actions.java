package com.wasitech.permissioncenter.java.com.wasitech.assist.command.family;

import java.util.Stack;

public class Actions {
    public static Stack<String>stack;
    public Actions(){
        stack=new Stack<>();
    }
    public void choose(String str){
        String temp;
        As(str);
        Bs(str);
        Cs(str);
        Ds(str);
        Es(str);
        Fs(str);
        Gs(str);
        Hs(str);
        Is(str);
        Js(str);
        Ls(str);
        Ms(str);
        Ns(str);
        Os(str);
        Ps(str);
        Qs(str);
        Rs(str);
        Ss(str);
        Ts(str);
        Us(str);
        Vs(str);
        Ws(str);
        Xs(str);
        Ys(str);
        Zs(str);
    }

    private void As(String str){
        String temp;
        // ACT
        if(ActionVerbs.act(str)){ }
        // ANSWER
        if(ActionVerbs.answer(str)){
            temp=str.replace("answer","");
            if(ActionWords.call(str)){
                temp=temp.replace("call","");
                stack.push(temp);
                stack.push(CONST.ACTION_CALL_ACCEPT);
            }
        }
        // APPROVE
        if(ActionVerbs.approve(str)){ }
        // ARRANGE
        if(ActionVerbs.arrange(str)){ }
    }
    private void Bs(String str){
        String temp;
        // BACK
        if(ActionVerbs.back(str)){
            temp=str.replace("back","");
            if(ActionWords.song(str)){
                temp=temp.replace("song","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_PREVIOUS);
            }
        }
        // BATHE
        if(ActionVerbs.bathe(str)){ }
        // BOW
        if(ActionVerbs.bow(str)){ }
        // BREAK
        if(ActionVerbs.break_(str)){ }
        // BUILD
        if(ActionVerbs.build(str)){ }
        // BUY
        if(ActionVerbs.buy(str)){ }
        // BYE
        if(ActionVerbs.bye(str)){
            temp=str.replace("bye","");
            if(ActionWords.good(str)){
                stack.push(temp);
                stack.push(CONST.ACTION_BYE);
            }
        }
    }
    private void Cs(String str){
        String temp;
        // CALL
        if(ActionWords.call(str)){
            temp=str.replace("call","");
            if(ActionWords.make(str)) {
                temp=temp.replace("make","");
            }
            stack.push(temp);
            stack.push(CONST.ACTION_CALL_MAKE);
        }
        // CATCH
        if(ActionVerbs.catch_(str)){ }
        // CLAP
        if(ActionVerbs.clap(str)){ }
        // CLIMB
        if(ActionVerbs.climb(str)){ }
        // CLOSE
        if(ActionVerbs.close(str)){ }
        // COACH
        if(ActionVerbs.coach(str)){ }
        // COLOR
        if(ActionVerbs.color(str)){ }
        // COMPLETE
        if(ActionVerbs.complete(str)){ }
        // CONCERN
        if(ActionVerbs.concern(str)){ }
        // CONNECT
        if(ActionVerbs.connect(str)){
            temp=str.replace("connect","");
            if(ActionWords.bluetooth(str)){
                temp=temp.replace("bluetooth","");
                stack.push(temp);
                stack.push(CONST.ACTION_BLUETOOTH_CONNECT);
            }
        }
        // COOK
        if(ActionVerbs.cook(str)){ }
        // COUGH
        if(ActionVerbs.cough(str)){ }
        // CRAWL
        if(ActionVerbs.crawl(str)){ }
        // CREATE
        if(ActionVerbs.create(str)){
            temp=str.replace("create","");
            if(ActionWords.contact(str)) {
                temp=temp.replace("contact","");
                stack.push(temp);
                stack.push(CONST.ACTION_CONTACT_CREATE);
            }

        }
        // CRY
        if(ActionVerbs.cry(str)){ }
        // CUT
        if(ActionVerbs.cut(str)){ }
    }
    private void Ds(String str){
        String temp;
        // DISCONNECT
        if(ActionVerbs.disconnect(str)){
            temp=str.replace("disconnect","");
            if(ActionWords.bluetooth(str)){
                temp=temp.replace("bluetooth","");
                stack.push(temp);
                stack.push(CONST.ACTION_BLUETOOTH_DISCONNECT);
            }
        }
// DANCE
        if(ActionVerbs.dance(str)){ }
// DESCRIBE
        if(ActionVerbs.describe(str)){ }
// DRINK
        if(ActionVerbs.drink(str)){ }
// DRAW
        if(ActionVerbs.draw(str)){ }
// DIAL
        if(ActionVerbs.dial(str)){
            temp=str.replace("dial","");
            if(ActionWords.number(str)) {
                temp=temp.replace("number","");
                stack.push(temp);
                stack.push(CONST.ACTION_CALL_MAKE);
            }

        }
// DECIDE
        if(ActionVerbs.decide(str)){ }
// DETECT
        if(ActionVerbs.detect(str)){
            temp=str.replace("detect","");
            if(ActionWords.wrong(str)&& ActionWords.password(str)){
                temp=temp.replace("wrong","");
                temp=temp.replace("password","");
                stack.push(temp);
                stack.push(CONST.ACTION_WRONG_PASSWORD);
            }
        }
// DOUBT
        if(ActionVerbs.doubt(str)){ }
// DISLIKE
        if(ActionVerbs.dislike(str)){ }
// DELETE
        if(ActionVerbs.delete(str)){
            boolean t=false;
            temp=str.replace("delete","");
            if (ActionWords.song(str)) {
                t=true;
                temp=temp.replace("song","");
            }
            else if (ActionWords.music(str)) {
                t=true;
                temp=temp.replace("music","");
            } else if (ActionWords.audio(str)) {
                t=true;
                temp=temp.replace("audio","");
            } else if (ActionWords.video(str)) {
                t=true;
                temp=temp.replace("video","");
            }
            if(ActionWords.application(str)){
                t=true;
                temp=temp.replace("application","");
                temp=temp.replace("app","");
            }
            if(ActionWords.movie(str)){
                t=true;
                temp=temp.replace("movie","");
            }
            if(ActionWords.film(str)){
                t=true;
                temp=temp.replace("film","");
            }
            if(ActionWords.document(str)){
                t=true;
                temp=temp.replace("document","");
            }
            if(ActionWords.file(str)){
                t=true;
                temp=temp.replace("file","");
            }
            if(t){
                if(temp.trim().equals("")||temp.trim().equals("s")||temp.trim().equals("'s")){
                    stack.push(" ");
                    stack.push(CONST.ACTION_CANT_DELETE);
                }
            }
        }
    }
    private void Es(String str){
        String temp;
        // EAT
        if(ActionVerbs.eat(str)){ }
// EDIT
        if(ActionVerbs.edit(str)){
            temp=str.replace("edit","");
            if(ActionWords.number(str)|| ActionWords.contact(str)) {
                temp=temp.replace("number","");
                temp=temp.replace("contact","");
                stack.push(temp);
                stack.push(CONST.ACTION_CONTACT_EDIT);
            }


        }
// ENTER
        if(ActionVerbs.enter(str)){ }
// EXIT
        if(ActionVerbs.exit(str)){ }
    }
    private void Fs(String str){
        String temp;
        // FORECAST
        if(ActionVerbs.forecast(str)){
            temp=str.replace("forecast","");
            if(ActionWords.weather(str)){
                temp=temp.replace("weather","");
                stack.push(temp);
                stack.push(CONST.ACTION_WEATHER_FORECAST);
            }
        }
// FEEL
        if(ActionVerbs.feel(str)){ }
// FORGET
        if(ActionVerbs.forget(str)){ }
// FIND
        if(ActionVerbs.find(str)){
            temp=str.replace("find","");
            if(ActionWords.contact(str)|| ActionWords.number(str)) {
                temp=temp.replace("contact","");
                temp=temp.replace("number","");
                stack.push(temp);
                stack.push(CONST.ACTION_CONTACT_FIND);
            }
            else if(ActionWords.song(str)|| ActionWords.audio(str)|| ActionWords.music(str)) {
                if(ActionWords.online(str)||(ActionWords.net(str)&& ActionWords.on(str))){
                    temp=temp.replace("song","");
                    temp=temp.replace("audio","");
                    temp=temp.replace("music","");
                    temp=temp.replace("online","");
                    temp=temp.replace("on net","");
                    stack.push(temp);
                    stack.push(CONST.ACTION_SONG_SEARCH);
                }
                else{
                    temp=temp.replace("song","");
                    temp=temp.replace("audio","");
                    temp=temp.replace("music","");
                    stack.push(temp);
                    stack.push(CONST.ACTION_SONG_FIND);
                }
            }
            else if(ActionWords.document(str)|| ActionWords.file(str)) {
                temp=temp.replace("document","");
                temp=temp.replace("file","");
                stack.push(temp);
                stack.push(CONST.ACTION_DOCUMENT_FIND);
            }
            else if(ActionWords.application(str)){
                temp=temp.replace("app","");
                stack.push(temp);
                stack.push(CONST.ACTION_APPLICATION_FIND);
            }
            else if(ActionWords.video(str)){
                temp=temp.replace("video","");
                stack.push(temp);
                stack.push(CONST.ACTION_VIDEO_SEARCH);
            }
        }
    }
    private void Gs(String str){
        String temp;
        // Give
        if(ActionVerbs.give(str)){ }
        // Grow
        if(ActionVerbs.grow(str)){ }
        // Go
        if(ActionVerbs.go(str)){ }
    }
    private void Hs(String str){
        String temp;

// HATE
        if(ActionVerbs.hate(str)){ }
// HEAR
        if(ActionVerbs.hear(str)){ }
// HOPE
        if(ActionVerbs.hope(str)){ }
    }
    private void Is(String str){
        String temp;
        // INVENT
        if(ActionVerbs.invent(str)){ }
// IMPRESS
        if(ActionVerbs.impress(str)){ }
    }
    private void Js(String str){
        String temp;
        // JUMP
        if(ActionVerbs.jump(str)){ }
    }
    private void Ks(String str){
        String temp;
        // Knit
        if(ActionVerbs.knit(str)){ }
        // Kiss
        if(ActionVerbs.kiss(str)){ }
// KNOW
        if(ActionVerbs.know(str)){ }
    }
    private void Ls(String str){
        String temp;
// LAUGH
        if(ActionVerbs.laugh(str)){ }
// LIE
        if(ActionVerbs.lie(str)){ }
// LEARN
        if(ActionVerbs.learn(str)){ }
// LIKE
        if(ActionVerbs.like(str)){ }
// LISTEN
        if(ActionVerbs.listen(str)){ }
// LOOK
        if(ActionVerbs.look(str)){ }
// LOVE
        if(ActionVerbs.love(str)){ }
    }
    private void Ms(String str){
        String temp;// MAKE
        if(ActionVerbs.make(str)){
            temp=str.replace("make","");
            if(ActionWords.call(str)){
                temp=temp.replace("call","");
                stack.push(temp);
                stack.push(CONST.ACTION_CALL_MAKE);
            }
        }
// MIND
        if(ActionVerbs.mind(str)){ }
// MUTE
        if(ActionVerbs.mute(str)){
            if(ComFuns.modesName(str)){
                stack.push(str);
                stack.push(CONST.ACTION_MODE_MUTE);
            }
            else if(ActionWords.video(str)|| ActionWords.audio(str)|| ActionWords.music(str)){
                stack.push(str);
                stack.push(CONST.ACTION_MEDIA_MUTE);
            }


        }
    }
    private void Ns(String str){
        String temp;
        // NOTICE
        if(ActionVerbs.notice(str)){ }
// NEXT
        if(ActionVerbs.next(str)){
            temp=str.replace("next","");
            if(ActionWords.song(str)|| ActionWords.music(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_NEXT);
            }
        }
    }
    private void Os(String str){
        String temp;
        // OPEN
        if(ActionVerbs.open(str)){
            temp=str.replace("open","");
            if(ActionWords.file(str)|| ActionWords.document(str)){
                temp=temp.replace("file","");
                temp=temp.replace("document","");
                stack.push(temp);
                stack.push(CONST.ACTION_DOCUMENT_OPEN);
            }
            else if(ActionWords.chat(str)){
                temp=temp.replace("chat","");
                stack.push(temp);
                stack.push(CONST.ACTION_CHAT_OPEN);
            }
            else{
                stack.push(temp);
                stack.push(CONST.ACTION_APPLICATION_OPEN);
            }
        }
// OWN
        if(ActionVerbs.own(str)){ }
// ON
        if(ActionVerbs.on(str)){
            temp=str;
            if(ActionWords.torch(str)|| ActionWords.light(str)||(ActionWords.flash(str)&& ActionWords.light(str))){
                temp=temp.replace("torch","");
                temp=temp.replace("flash","");
                temp=temp.replace("light","");
                stack.push(temp);
                stack.push(CONST.ACTION_FLASH_TURN);
            }
            else if(ActionWords.bluetooth(str)){
                temp=temp.replace("bluetooth","");
                stack.push(temp);
                stack.push(CONST.ACTION_BLUETOOTH_TURN);
            }
            else if(ActionWords.camera(str)&& ActionWords.secret(str)){
                temp=temp.replace("camera","");
                temp=temp.replace("secret","");
                stack.push(temp);
                stack.push(CONST.ACTION_SECRET_CAMERA_TURN);
            }
            else if(ActionWords.song(str)|| ActionWords.music(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_TURN);
            }
            else if(ActionWords.background(str)){
                temp=temp.replace("background","");
                stack.push(temp);
                stack.push(CONST.ACTION_BACKGROUND_TURN);
            }
            else if(ActionWords.screen(str)||(ActionWords.screen(str)&& ActionWords.phone(str))){
                temp=temp.replace("screen","");
                temp=temp.replace("phone","");
                temp=temp.replace("mobile","");
                stack.push(temp);
                stack.push(CONST.ACTION_SCREEN_TURN);
            }
        }
// OFF
        if(ActionVerbs.off(str)){
            temp=str;
            if(ActionWords.torch(str)|| ActionWords.light(str)||(ActionWords.flash(str)&& ActionWords.light(str))){
                temp=temp.replace("torch","");
                temp=temp.replace("flash","");
                temp=temp.replace("light","");
                stack.push(temp);
                stack.push(CONST.ACTION_FLASH_TURN);
            }
            else if(ActionWords.bluetooth(str)){
                temp=temp.replace("bluetooth","");
                stack.push(temp);
                stack.push(CONST.ACTION_BLUETOOTH_TURN);
            }
            else if(ActionWords.camera(str)&& ActionWords.secret(str)){
                temp=temp.replace("camera","");
                temp=temp.replace("secret","");
                stack.push(temp);
                stack.push(CONST.ACTION_SECRET_CAMERA_TURN);
            }
            else if(ActionWords.song(str)|| ActionWords.music(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_TURN);
            }
            else if(ActionWords.background(str)){
                temp=temp.replace("background","");
                stack.push(temp);
                stack.push(CONST.ACTION_BACKGROUND_TURN);
            }
            else if(ActionWords.screen(str)||(ActionWords.screen(str)&& ActionWords.phone(str))){
                temp=temp.replace("screen","");
                temp=temp.replace("phone","");
                temp=temp.replace("mobile","");
                stack.push(temp);
                stack.push(CONST.ACTION_SCREEN_TURN);
            }
        }
    }
    private void Ps(String str){
        String temp;// PAINT
        if(ActionVerbs.paint(str)){ }
// PAUSE
        if(ActionVerbs.pause(str)){
            temp=str.replace("pause","");
            if(ActionWords.song(str)|| ActionWords.music(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_PAUSE);
            }
        }
// PREVIOUS
        if(ActionVerbs.previous(str)){
            temp=str.replace("previous","");
            if(ActionWords.song(str)|| ActionWords.music(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_PREVIOUS);
            }
        }
// PLAN
        if(ActionVerbs.plan(str)){ }
// PLAY
        if(ActionVerbs.play(str)){
            temp=str.replace("play","");
            if(ActionWords.song(str)|| ActionWords.music(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_PLAY);
            }
        }
// PERCEIVE
        if(ActionVerbs.perceive(str)){ }
// PROMISE
        if(ActionVerbs.promise(str)){ }
// PLEASE
        if(ActionVerbs.please(str)){ }
// PREFER
        if(ActionVerbs.prefer(str)){ }
    }
    private void Qs(String str){
        String temp;
    }
    private void Rs(String str){
        String temp;// READ
        if(ActionVerbs.read(str)){
            temp=str.replace("read","");
            if(ActionWords.notification(str)){
                temp=temp.replace("notification","");
                stack.push(temp);
                stack.push(CONST.ACTION_NOTIFICATION_READ);
            }

        }
// RESUME
        if(ActionVerbs.resume(str)){
            temp=str.replace("resume","");
            if(ActionWords.song(str)|| ActionWords.music(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_RESUME);
            }
        }
// REALIZE
        if(ActionVerbs.realize(str)){ }
// REPLAY
        if(ActionVerbs.replay(str)){
            temp=str.replace("replay","");
            if(ActionWords.song(str)|| ActionWords.music(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_REPLAY);
            }
        }
// RECOGNIZE
        if(ActionVerbs.recognize(str)){ }
// REMEMBER
        if(ActionVerbs.remember(str)){ }
// REPLACE
        if(ActionVerbs.replace(str)){ }
// RUN
        if(ActionVerbs.run(str)){
            temp=str.replace("run","");
            if(ActionWords.file(str)|| ActionWords.document(str)){
                temp=temp.replace("file","");
                temp=temp.replace("document","");
                stack.push(temp);
                stack.push(CONST.ACTION_DOCUMENT_OPEN);
            }
        }
// RECORD
        if(ActionVerbs.record(str)){
            temp=str.replace("record","");
            if(ActionWords.audio(str)|| ActionWords.voice(str)|| ActionWords.song(str)){
                temp=temp.replace("audio","");
                temp=temp.replace("voice","");
                temp=temp.replace("song","");
                stack.push(temp);
                stack.push(CONST.ACTION_AUDIO_RECORD);
            }
            else if(ActionWords.video(str)) {
                temp=temp.replace("video","");
                stack.push(temp);
                stack.push(CONST.ACTION_VIDEO_RECORD);
            }
            else if(ActionWords.call(str)) {
                temp=temp.replace("call","");
                stack.push(temp);
                stack.push(CONST.ACTION_CALL_RECORD);
            }
        }
    }
    private void Ss(String str){
        String temp;
// SCREAM
        if(ActionVerbs.scream(str)){ }
// SEE
        if(ActionVerbs.see(str)){ }
// SHOP
        if(ActionVerbs.shop(str)){ }
// SHOUT
        if(ActionVerbs.shout(str)){ }
// SING
        if(ActionVerbs.sing(str)){ }
// SKIP
        if(ActionVerbs.skip(str)){ }
// STOP
        if(ActionVerbs.stop(str)){
            temp=str.replace("stop","");
            if(ActionWords.song(str)|| ActionWords.music(str)|| ActionWords.audio(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                temp=temp.replace("audio","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_STOP);
            }
            else if(ActionWords.video(str)){
                temp=temp.replace("video","");
                stack.push(temp);
                stack.push(CONST.ACTION_VIDEO_STOP);
            }
            else if(temp.equals("")||ComFuns.AppNames(temp)){
                stack.push(temp);
                stack.push(CONST.ACTION_STOP);
            }
        }
// SLEEP
        if(ActionVerbs.sleep(str)){ }
// SNEEZE
        if(ActionVerbs.sneeze(str)){ }
// SOLVE
        if(ActionVerbs.solve(str)){ }
// STUDY
        if(ActionVerbs.study(str)){ }
// SET
        if(ActionVerbs.set(str)){
            if(ActionWords.alarm(str)){
                temp=str.replace("set","");
                temp=temp.replace("alarm","");
                stack.push(temp);
                stack.push(CONST.ACTION_ALARM_SET);
            }
            else if(ActionWords.reminder(str)){
                temp=str.replace("set","");
                temp=temp.replace("reminder","");
                stack.push(temp);
                stack.push(CONST.ACTION_REMINDER_SET);
            }
            else if(ActionWords.timer(str)) {
                temp=str.replace("set","");
                temp=temp.replace("timer","");
                stack.push(temp);
                stack.push(CONST.ACTION_TIMER_SET);
            }
        }
// SEND
        if(ActionVerbs.send(str)){
            temp=str.replace("send","");
            if(ActionWords.sms(str)|| ActionWords.message(str)){
                temp=temp.replace("sms","");
                temp=temp.replace("message","");
                stack.push(temp);
                stack.push(CONST.ACTION_SMS_SEND);
            }
            else if(ActionWords.audio(str)){
                temp=temp.replace("audio","");
                stack.push(temp);
                stack.push(CONST.ACTION_AUDIO_SEND);
            }
            else if(ActionWords.video(str)) {
                temp=temp.replace("video","");
                stack.push(temp);
                stack.push(CONST.ACTION_VIDEO_SEND);
            }
            else if(ActionWords.mail(str)){
                temp=temp.replace("email","");
                temp=temp.replace("mail","");
                stack.push(temp);
                stack.push(CONST.ACTION_EMAIL_SEND);
            }
        }
// SEARCH
        if(ActionVerbs.search(str)){
            temp=str.replace("search","");
            if(ActionWords.application(str)) {
                temp=temp.replace("application","");
                temp=temp.replace("app","");
                stack.push(temp);
                stack.push(CONST.ACTION_APPLICATION_FIND);
            }
            else if(ActionWords.number(str)|| ActionWords.contact(str)){
                temp=temp.replace("number","");
                temp=temp.replace("contact","");
                stack.push(temp);
                stack.push(CONST.ACTION_CONTACT_FIND);
            }
            else if(ActionWords.song(str)|| ActionWords.audio(str)|| ActionWords.music(str)) {
                if(ActionWords.online(str)||(ActionWords.net(str)&& ActionWords.on(str))){
                    temp=temp.replace("song","");
                    temp=temp.replace("audio","");
                    temp=temp.replace("music","");
                    temp=temp.replace("online","");
                    temp=temp.replace("on net","");
                    stack.push(temp);
                    stack.push(CONST.ACTION_SONG_SEARCH);
                }
                else{
                    temp=temp.replace("song","");
                    temp=temp.replace("audio","");
                    temp=temp.replace("music","");
                    stack.push(temp);
                    stack.push(CONST.ACTION_SONG_FIND);
                }

            }
            else if(ActionWords.video(str)){
                temp=temp.replace("video","");
                stack.push(temp);
                stack.push(CONST.ACTION_VIDEO_SEARCH);
            }
            else {
                stack.push(temp);
                stack.push(CONST.ACTION_SEARCH);
            }
        }
// SHOW
        if(ActionVerbs.show(str)){
            temp=str.replace("show","");
            if(ActionWords.contact(str)|| ActionWords.number(str)) {
                temp=temp.replace("contact","");
                temp=temp.replace("number","");
                stack.push(temp);
                stack.push(CONST.ACTION_CONTACT_SHOW);
            }
            else if(ActionWords.audio(str)|| ActionWords.music(str)|| ActionWords.song(str)){
                temp=temp.replace("audio","");
                temp=temp.replace("music","");
                temp=temp.replace("song","");
                temp=temp.replace("list","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_SHOW);
            }
            else if(ActionWords.alarm(str)){
                temp=temp.replace("alarm","");
                temp=temp.replace("list","");
                stack.push(temp);
                stack.push(CONST.ACTION_ALARM_SHOW);
            }
            else if(ActionWords.weather(str)){
                temp=temp.replace("weather","");
                stack.push(temp);
                stack.push(CONST.ACTION_WEATHER_SHOW);
            }
            else if(ActionWords.video(str)){
                temp=temp.replace("video","");
                temp=temp.replace("list","");
                stack.push(temp);
                stack.push(CONST.ACTION_VIDEO_SHOW);
            }
            else if(ActionWords.call(str)){
                temp=temp.replace("call","");
                temp=temp.replace("log","");
                stack.push(temp);
                stack.push(CONST.ACTION_CALL_SHOW);
            }
            else if(ActionWords.screen(str)&& ActionWords.shot(str)){
                temp=temp.replace("screen","");
                temp=temp.replace("shot","");
                stack.push(temp);
                stack.push(CONST.ACTION_SCREEN_SHOT_SHOW);
            }
            else if(ActionWords.picture(str)|| ActionWords.photo(str)|| ActionWords.snap(str)){
                temp=temp.replace("pic","");
                temp=temp.replace("photo","");
                temp=temp.replace("snap","");
                stack.push(temp);
                stack.push(CONST.ACTION_PICTURE_SHOW);
            }

        }
// SMELL
        if(ActionVerbs.smell(str)){ }
// SURPRISE
        if(ActionVerbs.surprise(str)){ }
    }
    private void Ts(String str){
        String temp;// THINK
        if(ActionVerbs.think(str)){ }
// TEACH
        if(ActionVerbs.teach(str)){ }
// TOUCH
        if(ActionVerbs.touch(str)){ }
// TURN
        if(ActionVerbs.turn(str)){
            temp=str.replace("turn","");
            if(ActionWords.torch(str)|| ActionWords.light(str)||(ActionWords.flash(str)&& ActionWords.light(str))){
                temp=temp.replace("torch","");
                temp=temp.replace("flash","");
                temp=temp.replace("light","");
                stack.push(temp);
                stack.push(CONST.ACTION_FLASH_TURN);
            }
            else if(ActionWords.bluetooth(str)){
                temp=temp.replace("bluetooth","");
                stack.push(temp);
                stack.push(CONST.ACTION_BLUETOOTH_TURN);
            }
            else if(ActionWords.camera(str)&& ActionWords.secret(str)){
                temp=temp.replace("camera","");
                temp=temp.replace("secret","");
                stack.push(temp);
                stack.push(CONST.ACTION_SECRET_CAMERA_TURN);
            }
            else if(ActionWords.song(str)|| ActionWords.music(str)){
                temp=temp.replace("song","");
                temp=temp.replace("music","");
                stack.push(temp);
                stack.push(CONST.ACTION_SONG_TURN);
            }
            else if(ActionWords.background(str)){
                temp=temp.replace("background","");
                stack.push(temp);
                stack.push(CONST.ACTION_BACKGROUND_TURN);
            }
            else if(ActionWords.screen(str)||(ActionWords.screen(str)&& ActionWords.phone(str))){
                temp=temp.replace("screen","");
                temp=temp.replace("phone","");
                temp=temp.replace("mobile","");
                stack.push(temp);
                stack.push(CONST.ACTION_SCREEN_TURN);
            }
        }
// TAKE
        if(ActionVerbs.take(str)){
            temp=str.replace("take","");
            if(ActionWords.photo(str)|| ActionWords.picture(str)|| ActionWords.snap(str)) {
                temp=temp.replace("photo","");
                temp=temp.replace("pic","");
                temp=temp.replace("snap","");
                stack.push(temp);
                stack.push(CONST.ACTION_PICTURE_TAKE);
            }
            else if(ActionWords.shot(str)&& ActionWords.screen(str)) {
                temp=temp.replace("screen","");
                temp=temp.replace("shot","");
                stack.push(temp);
                stack.push(CONST.ACTION_SCREEN_SHOT);
            }

        }
    }
    private void Us(String str){
        String temp;
// UNDERSTAND
        if(ActionVerbs.understand(str)){ }
    }
    private void Vs(String str){
        String temp;
    }
    private void Ws(String str){
        String temp;
// WALK
        if(ActionVerbs.walk(str)){ }
// WIN
        if(ActionVerbs.win(str)){ }
// WAKE
        if(ActionVerbs.wake(str)){
            temp=str.replace("wake","");
            if(ActionWords.me(str)){
                temp=temp.replace("me","");
                stack.push(temp);
                stack.push(CONST.ACTION_ALARM_WAKE);
            }
        }
    }
    private void Xs(String str){
        String temp;
    }
    private void Ys(String str){
        String temp;
        // Yank
        if(ActionVerbs.yank(str)){ }
    }
    private void Zs(String str){
        String temp;
        // ZIP
        if(ActionVerbs.zip(str)){ }
    }

    private void actionsToBeAdded(){
        // Dance, Describe, Draw, Drink, Drive, Dive, Dream, Dig, Decide, Dislike, Doubt
        // Eat, Edit, Enter, Exit
        // FIGHT, Fly, Feel, Forget
        // Hug, Hate, Hear, Hope, Hold
        // Imitate,	Invent, Impress
        // Laugh, Lie, Listen, Learn, Like, Look, Love
        // Paint, Plan,	Play, Perceive, Please, Prefer, Promise
        // Read, Replace, Run, RIDE, Realize, Recognize, Remember
        // Scream, See, Shop, Shout, Sing, Skip, Sneeze, Solve, Study, Shake, Ski, Stack, Sew, Smell, Sleep, Snore, SIT DOWN, STAND UP, Surprise
        // Teach, Touch, Turn, Turn on, Turn off, Throw away, Talk, Think
        // Walk, Win, Write, Whistle, Wash, Wait, Win, Write, Watch TV, WALK
    }
    public static class ActionVerbs {
        public static boolean act(String str) {
         return (str.contains("act"));
        }
        public static boolean answer(String str) {
         return (str.contains("answer"));
        }
        public static boolean approve(String str) {
         return (str.contains("approve"));
        }
        public static boolean arrange(String str) {
         return (str.contains("arrange"));
        }
        public static boolean back(String str) {
         return (str.contains("back"));
        }
        public static boolean bow(String str) {
         return (str.contains("bow"));
        }
        public static boolean buy(String str) {
         return (str.contains("buy"));
        }
        public static boolean build(String str) {
         return (str.contains("build"));
        }
        public static boolean break_(String str) {
         return (str.contains("break"));
        }
        public static boolean bathe(String str) {
         return (str.contains("bathe"));
        }
        public static boolean bye(String str) {
         return (str.contains("bye"));
        }
        public static boolean create(String str) {
         return (str.contains("create"));
        }
        public static boolean cry(String str) {
         return (str.contains("cry"));
        }
        public static boolean cut(String str) {
         return (str.contains("cut"));
        }
        public static boolean cough(String str) {
         return (str.contains("cough"));
        }
        public static boolean coach(String str) {
         return (str.contains("coach"));
        }
        public static boolean close(String str) {
         return (str.contains("close"));
        }
        public static boolean color(String str) {
         return (str.contains("color"));
        }
        public static boolean clap(String str) {
         return (str.contains("clap"));
        }
        public static boolean crawl(String str) {
         return (str.contains("crawl"));
        }
        public static boolean climb(String str) {
         return (str.contains("climb"));
        }
        public static boolean catch_(String str) {
         return (str.contains("catch"));
        }
        public static boolean cook(String str) {
         return (str.contains("cook"));
        }
        public static boolean complete(String str) {
         return (str.contains("complete"));
        }
        public static boolean concern(String str) {
         return (str.contains("concern"));
        }
        public static boolean connect(String str) {
            return (str.contains("connect") && !str.contains("disconnect"));
        }
        public static boolean disconnect(String str) {
            return (str.contains("disconnect"));
        }
        public static boolean dance(String str) {
         return (str.contains("dance"));
        }
        public static boolean describe(String str) {
         return (str.contains("describe"));
        }
        public static boolean drink(String str) {
         return (str.contains("drink"));
        }
        public static boolean draw(String str) {
         return (str.contains("draw"));
        }
        public static boolean decide(String str) {
         return (str.contains("decide"));
        }
        public static boolean detect(String str) {
         return (str.contains("detect"));
        }
        public static boolean dial(String str) {
         return (str.contains("dial"));
        }
        public static boolean doubt(String str) {
         return (str.contains("doubt"));
        }
        public static boolean dislike(String str) {
         return (str.contains("dislike"));
        }
        public static boolean delete(String str) {
            return (str.contains("delete"));
        }
        public static boolean eat(String str) {
         return (str.contains("eat"));
        }
        public static boolean edit(String str) {
         return (str.contains("edit"));
        }
        public static boolean enter(String str) {
         return (str.contains("enter"));
        }
        public static boolean exit(String str) {
         return (str.contains("exit"));
        }
        public static boolean forecast(String str) {
         return (str.contains("forecast"));
        }
        public static boolean feel(String str) {
         return (str.contains("feel"));
        }
        public static boolean forget(String str) {
         return (str.contains("forget"));
        }
        public static boolean find(String str) {
         return (str.contains("find"));
        }
        public static boolean go(String str) {
         return (str.contains("go"));
        }
        public static boolean grow(String str) {
         return (str.contains("grow"));
        }
        public static boolean give(String str) {
         return (str.contains("give"));
        }
        public static boolean hate(String str) {
         return (str.contains("hate"));
        }
        public static boolean hear(String str) {
         return (str.contains("hear"));
        }
        public static boolean hope(String str) {
         return (str.contains("hope"));
        }
        public static boolean invent(String str) {
         return (str.contains("invent"));
        }
        public static boolean impress(String str) {
         return (str.contains("impress"));
        }
        public static boolean jump(String str) {
         return (str.contains("jump"));
        }
        public static boolean know(String str) {
         return (str.contains("know"));
        }
        public static boolean knit(String str) {
         return (str.contains("knit"));
        }
        public static boolean kiss(String str) {
         return (str.contains("kiss"));
        }
        public static boolean laugh(String str) {
         return (str.contains("laugh"));
        }
        public static boolean lie(String str) {
         return (str.contains("lie"));
        }
        public static boolean like(String str) {
         return (str.contains("like"));
        }
        public static boolean listen(String str) {
         return (str.contains("listen"));
        }
        public static boolean look(String str) {
         return (str.contains("look"));
        }
        public static boolean love(String str) {
         return (str.contains("love"));
        }
        public static boolean learn(String str) {
         return (str.contains("learn"));
        }
        public static boolean make(String str) {
         return (str.contains("make"));
        }
        public static boolean mind(String str) {
         return (str.contains("mind"));
        }
        public static boolean mute(String str) {
         return (str.contains("mute"));
        }
        public static boolean notice(String str) {
         return (str.contains("notice"));
        }
        public static boolean next(String str) {
         return (str.contains("next"));
        }
        public static boolean open(String str) {
         return (str.contains("open"));
        }
        public static boolean own(String str) {
         return (str.contains("own"));
        }
        public static boolean on(String str) { return (str.contains(" on")  || str.startsWith("on ") ); }
        public static boolean off(String str) {
         return (str.contains("off"));
        }
        public static boolean paint(String str) {
         return (str.contains("paint"));
        }
        public static boolean plan(String str) {
         return (str.contains("plan"));
        }
        public static boolean play(String str) { return (str.contains("play")&&!str.contains("replay")); }
        public static boolean pause(String str) {
         return (str.contains("pause"));
        }
        public static boolean previous(String str) {
         return (str.contains("previous"));
        }
        public static boolean perceive(String str) {
         return (str.contains("perceive"));
        }
        public static boolean promise(String str) {
         return (str.contains("promise"));
        }
        public static boolean prefer(String str) {
         return (str.contains("prefer"));
        }
        public static boolean please(String str) {
         return (str.contains("please"));
        }
        public static boolean read(String str) {
         return (str.contains("read"));
        }
        public static boolean resume(String str) {
         return (str.contains("resume"));
        }
        public static boolean realize(String str) {
         return (str.contains("realize"));
        }
        public static boolean remember(String str) {
         return (str.contains("remember"));
        }
        public static boolean recognize(String str) { return (str.contains("recognize")||str.contains("recognise")); }
        public static boolean replace(String str) {
         return (str.contains("replace"));
        }
        public static boolean replay(String str) {
            return (str.contains("replay"));
        }
        public static boolean record(String str) {
         return (str.contains("record"));
        }
        public static boolean run(String str) {
         return (str.contains("run"));
        }
        public static boolean see(String str) {
         return (str.contains("see"));
        }
        public static boolean scream(String str) {
            return (str.contains("scream"));
        }
        public static boolean sing(String str) {
            return (str.contains("sing"));
        }
        public static boolean shout(String str) {
            return (str.contains("shout"));
        }
        public static boolean shop(String str) {
            return (str.contains("shop"));
        }
        public static boolean skip(String str) {
            return (str.contains("skip"));
        }
        public static boolean sleep(String str) {
            return (str.contains("sleep"));
        }
        public static boolean sneeze(String str) {
            return (str.contains("sneeze"));
        }
        public static boolean solve(String str) {
            return (str.contains("solve"));
        }
        public static boolean study(String str) {
            return (str.contains("study"));
        }
        public static boolean set(String str) {
            return (str.contains("set"));
        }
        public static boolean stop(String str) {
            return (str.contains("stop"));
        }
        public static boolean send(String str) {
            return (str.contains("send"));
        }
        public static boolean search(String str) {
            return (str.contains("search"));
        }
        public static boolean show(String str) {
            return (str.contains("show"));
        }
        public static boolean smell(String str) {
            return (str.contains("smell"));
        }
        public static boolean surprise(String str) {
            return (str.contains("surprise"));
        }
        public static boolean think(String str) {
            return (str.contains("think"));
        }
        public static boolean teach(String str) {
            return (str.contains("teach"));
        }
        public static boolean touch(String str) {
            return (str.contains("touch"));
        }
        public static boolean turn(String str) {
            return (str.contains("turn"));
        }
        public static boolean take(String str) {
            return (str.contains("take"));
        }
        public static boolean understand(String str) {
            return (str.contains("understand"));
        }
        public static boolean walk(String str) {
            return (str.contains("walk"));
        }
        public static boolean wake(String str) {
            return (str.contains("wake"));
        }
        public static boolean win(String str) {
            return (str.contains("win"));
        }
        public static boolean yank(String str) {
            return (str.contains("yank"));
        }
        public static boolean zip(String str) {
            return (str.contains("zip"));
        }

    }
    public static class ActionWords{
        public static boolean call(String str) {
            return (str.contains("call"));
        }
        public static boolean contact(String str) {
            return (str.contains("contact"));
        }
        public static boolean number(String str) {
            return (str.contains("number"));
        }
        public static boolean weather(String str) {
            return (str.contains("weather"));
        }
        public static boolean song(String str) { return (str.contains("song")); }
        public static boolean document(String str) {
            return (str.contains("document"));
        }
        public static boolean application(String str) {
            return (str.contains("app"));
        }
        public static boolean file(String str) {
            return (str.contains("file"));
        }
        public static boolean chat(String str) {
            return (str.contains("chat"));
        }
        public static boolean music(String str) {
            return (str.contains("music"));
        }
        public static boolean audio(String str) {
            return (str.contains("audio"));
        }
        public static boolean video(String str) {
            return (str.contains("video"));
        }
        public static boolean voice(String str) {
            return (str.contains("voice"));
        }
        public static boolean alarm(String str) {
            return (str.contains("alarm"));
        }
        public static boolean reminder(String str) {
            return (str.contains("reminder"));
        }
        public static boolean timer(String str) {
            return (str.contains("timer"));
        }
        public static boolean sms(String str) {
            return (str.contains("sms"));
        }
        public static boolean message(String str) {
            return (str.contains("message"));
        }
        public static boolean mail(String str) {
            return (str.contains("mail"));
        }
        public static boolean photo(String str) {
            return (str.contains("photo"));
        }
        public static boolean picture(String str) {
            return (str.contains("pic"));
        }
        public static boolean snap(String str) {
            return (str.contains("snap"));
        }
        public static boolean shot(String str) {
            return (str.contains("shot"));
        }
        public static boolean screen(String str) {
            return (str.contains("screen"));
        }
        public static boolean wrong(String str) {
            return (str.contains("wrong"));
        }
        public static boolean password(String str) {
            return (str.contains("password"));
        }
        public static boolean check(String str) {
            return (str.contains("check"));
        }
        public static boolean good(String str) {
            return (str.contains("good"));
        }
        public static boolean off(String str) { return (str.contains("off") || str.contains("stop")||  str.contains("deactivate") || str.contains("disable")); }
        public static boolean on(String str) { return (str.contains(" on") || str.contains("start") || (str.contains("activate")  && !str.contains("deactivate") )|| str.contains("enable")); }
        public static boolean torch(String str) {
            return ( str.contains("torch") );
        }
        public static boolean flash(String str) {
            return (str.contains("flash"));
        }
        public static boolean light(String str) {
            return (str.contains("light"));
        }
        public static boolean bluetooth(String str) {
            return (str.contains("bluetooth"));
        }
        public static boolean secret(String str) {
            return (str.contains("secret"));
        }
        public static boolean camera(String str) {
            return (str.contains("camera"));
        }
        public static boolean background(String str) {
            return (str.contains("background"));
        }
        public static boolean phone(String str) { return (str.contains("phone")||str.contains("mobile")); }
        public static boolean notification(String str) {
            return (str.contains("notification"));
        }
        public static boolean me(String str) {
            return (str.contains("me"));
        }
        public static boolean online(String str) {
            return (str.contains("online"));
        }
        public static boolean movie(String str) {
            return (str.contains("movie"));
        }
        public static boolean film(String str) {
            return (str.contains("film"));
        }
        public static boolean net(String str) {
            return (str.contains("net"));
        }
        public static boolean make(String str) { return (str.contains("make"));}
    }
    public static class CONST {
        public static final String ACTION_ALARM_SET = "set_alarm";
        public static final String ACTION_ALARM_SHOW = "show_alarm";
        public static final String ACTION_ALARM_WAKE = "wake_alarm";
        public static final String ACTION_AUDIO_RECORD = "record_audio";
        public static final String ACTION_AUDIO_SEND = "send_audio";
        public static final String ACTION_APPLICATION_FIND="find_application";
        public static final String ACTION_APPLICATION_OPEN="open_application";

        public static final String ACTION_BYE ="good_bye" ;
        public static final String ACTION_BACKGROUND_TURN ="turn_background" ;
        public static final String ACTION_BLUETOOTH_TURN ="turn_bluetooth" ;
        public static final String ACTION_BLUETOOTH_CONNECT ="connect_bluetooth" ;
        public static final String ACTION_BLUETOOTH_DISCONNECT ="disconnect_bluetooth" ;

        public static final String ACTION_CANT_DELETE = "cant_delete";
        public static final String ACTION_CHAT_OPEN ="open_chat" ;
        public static final String ACTION_CALL_MAKE ="make_call";
        public static final String ACTION_CALL_ACCEPT ="accept_call";
        public static final String ACTION_CALL_SHOW ="show_call";
        public static final String ACTION_CALL_RECORD ="record_call";
        public static final String ACTION_CONTACT_CREATE ="create_contact";
        public static final String ACTION_CONTACT_EDIT ="edit_contact";
        public static final String ACTION_CONTACT_FIND ="find_contact";
        public static final String ACTION_CONTACT_SHOW ="show_contact";

        public static final String ACTION_DOCUMENT_FIND ="find_document";
        public static final String ACTION_DOCUMENT_OPEN ="open_document";

        public static final String ACTION_EMAIL_SEND = "send_email";

        public static final String ACTION_FLASH_TURN = "turn_flash" ;

        public static final String ACTION_MEDIA_MUTE = "mute_media" ;
        public static final String ACTION_MODE_MUTE = "mute_mode" ;

        public static final String ACTION_NOTIFICATION_READ = "read_notification" ;

        public static final String ACTION_PICTURE_TAKE = "take_pic";
        public static final String ACTION_PICTURE_SHOW = "show_pic";

        public static final String ACTION_REMINDER_SET = "set_reminder";

        public static final String ACTION_SEARCH ="search" ;
        public static final String ACTION_STOP ="stop" ;
        public static final String ACTION_SMS_SEND= "send_sms";
        public static final String ACTION_SECRET_CAMERA_TURN ="turn_secret_camera" ;
        public static final String ACTION_SONG_FIND ="find_song";
        public static final String ACTION_SONG_PLAY="play_song";
        public static final String ACTION_SONG_PREVIOUS="previous_song";
        public static final String ACTION_SONG_NEXT="next_song";
        public static final String ACTION_SONG_PAUSE="pause_song";
        public static final String ACTION_SONG_TURN="turn_song";
        public static final String ACTION_SONG_RESUME="resume_song";
        public static final String ACTION_SONG_STOP="stop_song";
        public static final String ACTION_SONG_REPLAY="replay_song";
        public static final String ACTION_SONG_SHOW="show_song";
        public static final String ACTION_SONG_SEARCH="search_song";
        public static final String ACTION_SCREEN_SHOT = "screen_shot";
        public static final String ACTION_SCREEN_TURN = "turn_screen";
        public static final String ACTION_SCREEN_SHOT_SHOW = "show_screen_shot";

        public static final String ACTION_TIMER_SET = "set_timer";

        public static final String ACTION_VIDEO_RECORD = "record_video";
        public static final String ACTION_VIDEO_SEND = "send_video";
        public static final String ACTION_VIDEO_SEARCH = "search_video";
        public static final String ACTION_VIDEO_SHOW = "show_video";
        public static final String ACTION_VIDEO_STOP = "stop_video";

        public static final String ACTION_WEATHER_FORECAST ="forecast_weather";
        public static final String ACTION_WEATHER_SHOW="show_weather";
        public static final String ACTION_WRONG_PASSWORD = "wrong_password";
    }
}
