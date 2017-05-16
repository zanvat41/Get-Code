
/**
 * All of the JavaScript functions needed for the whole project
 * Created by tyler on 4/4/2017.
 */

//the function that is called when a user signs in
function onSignIn(googleUser) {

    // Useful data for your client-side scripts:
    var profile = googleUser.getBasicProfile();
    var firstName = profile.getGivenName();
    var lastName = profile.getFamilyName();
    var email = profile.getEmail();

    //log data
    console.log("ID: " + profile.getId()); // Don't send this directly to your server!
    console.log('Full Name: ' + profile.getName());
    console.log('Given Name: ' + profile.getGivenName());
    console.log('Family Name: ' + profile.getFamilyName());
    console.log("Image URL: " + profile.getImageUrl());
    console.log("Email: " + profile.getEmail());

    //add the form elements we need to send to the servlet to the DOM
    document.getElementById("firstName").value = firstName;
    document.getElementById("lastName").value = lastName;
    document.getElementById("email").value = email;

    //submit form to redirect
    document.getElementById("login_form").submit();
}

//the function that is called when a user signs out
function signOut(disconnect) {
    //sign the user out
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });

    //if the user is signing out forever, then remove their association to the site
    if (disconnect) {
        auth2.disconnect();
    }
}