var FCM = require('fcm-push');

var serverKey = 'AAAAat8s00Q:APA91bGixNVlrw1IAcTbS6tJSW_T0qiTiW-y8sUbv9RKvNdxbalrpJ9zzhnheLp0VOggRnClKzU_LsVwixUdO8C8MkzHJb4wO4EadDdTps3jDAMf-2X1Si6dNEwCgfrdi78dmNHi2-7J';
var fcm = new FCM(serverKey);

var message = {
    to: 'fj5dVaZB7mU:APA91bHqDzcVg4V2j93tSBMJD5KhXqnYVjif0qzaiWjOwxr2KM88cVeZCXFmgc7e68OYqg50VbWg_Tw8g76w4q8P-7PPNhH1xmLkOO6oN9P48uqFKRhUByHhfLnCpUlcs2FjOeiwKQ_h', // required fill with device token or topics
    collapse_key: '', 
    data: {
        your_custom_data_key: 'your_custom_data_value'
    },
    notification: {
        title: '오류상황',
        body: '고데기가 평소보다 오랫동안 사용되었습니다'
    }
};

//callback style
fcm.send(message, function(err, response){
    if (err) {
        console.log("Something has gone wrong!");
    } else {
        console.log("Successfully sent with response: ", response);
    }
});


