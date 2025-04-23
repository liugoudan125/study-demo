package com.beiming.domain;

/**
 * Domain
 */
public class Result {
//    {"currPage":1,"list":[{"i":"37aa5dc36d88444786b6b543845963ba","w":4182,"h":2304,"t":2},{"i":"1b87e2c5880511ebb6edd017c2d2eca2","w":3078,"h":1844,"t":1},{"i":"ba3dee63249d43e88b3d5c5e7c75ef23","w":6201,"h":4203,"t":2},{"i":"cc7204e9880411ebb6edd017c2d2eca2","w":3840,"h":2160,"t":2},{"i":"4d5b121d880511ebb6edd017c2d2eca2","w":3840,"h":2160,"t":1},{"i":"5af17f7f881b11ebb6edd017c2d2eca2","w":3996,"h":2250,"t":2},{"i":"2c09c340e57a4963ba60fdc0d809db8b","w":1920,"h":1142,"t":2},{"i":"94628268331f4a9b92c0c36f5f4b196b","w":3840,"h":2160,"t":2},{"i":"286484bdc06b47dab2f5cdb9a3ca86e2","w":2560,"h":1440,"t":2},{"i":"6931aa69219c47eea8991423b69d9ba3","w":8000,"h":4988,"t":2},{"i":"c9d3deb2880411ebb6edd017c2d2eca2","w":1920,"h":1080,"t":2},{"i":"6355071687f84cfcb2cb002f9073f5a4","w":2735,"h":1632,"t":2},{"i":"c324fa8a880411ebb6edd017c2d2eca2","w":1920,"h":1080,"t":2},{"i":"393204c88fff4715a4b4aa88aabdb2ed","w":5800,"h":2900,"t":2},{"i":"aa126b96881511ebb6edd017c2d2eca2","w":4800,"h":2748,"t":2},{"i":"47482bcb3a294e868546d6c73f1e7ca8","w":3840,"h":2160,"t":2},{"i":"a202c44c2075478dbfac39baeb800046","w":2560,"h":1440,"t":1},{"i":"559ad287880511ebb6edd017c2d2eca2","w":1920,"h":1080,"t":1},{"i":"8373377e69c3499d904877fab8c6f329","w":1920,"h":1264,"t":2},{"i":"5268d877a2a04864b36b4961ab793f4f","w":2560,"h":1440,"t":2},{"i":"3de7c790881611ebb6edd017c2d2eca2","w":4634,"h":3086,"t":2},{"i":"ccfdfe74880411ebb6edd017c2d2eca2","w":7680,"h":4320,"t":2},{"i":"bab9141327ca48e39abef6229b79cf9c","w":1920,"h":1080,"t":2},{"i":"3e70ceca883111ebb6edd017c2d2eca2","w":5120,"h":2160,"t":2}],"pageSize":24,"totalCount":41581,"totalPage":1733}
    private Integer code;
    private String message;
    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
