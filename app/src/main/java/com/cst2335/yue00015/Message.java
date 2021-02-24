package com.cst2335.yue00015;

public class Message {
        private String msg;
        private boolean isSend;
        protected long id;

        public Message (String msg, boolean isSend, long id){
            this.msg = msg;
            this.isSend = isSend;
            this.id = id;
        }

        public boolean getIsSend(){
            return isSend;
        }

        public long getId(){
            return id;
        }

        public String getMsg() {
            return msg;
        }
}
