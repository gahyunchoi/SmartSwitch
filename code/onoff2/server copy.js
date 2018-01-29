var FCM = require('fcm-push');

var serverKey = '';
var fcm = new FCM(serverKey);

var message = {
    to: 'eg7YnnUDJ9g:APA91bG8cojsMQ9VK_bR5bCyJbCTThHst5YVEAyuJMFvs_IQ7h1jDJaZRg2jSGArwx71eK1IwDLGOfgnybe4NkCtO4uM5lnSUBWjKffPBL3mo80_izvWx7mReKjTu6JZTzUyV88-WJ4Q', // required fill with device token or topics
    collapse_key: 'AIzaSyB__2AgxZ6lZrJbSMSEHDQIpDQseyl8XaU', 
    data: {
        your_custom_data_key: 'your_custom_data_value'
    },
    notification: {
        title: 'Title',
        body: 'Body'
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


