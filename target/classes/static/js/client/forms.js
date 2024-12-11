document.addEventListener('DOMContentLoaded', function () {
  const btnSendEmail = document.getElementById("btnSendEmail");
  const usernameInput = document.getElementById("username");

  async function sendEmail(){
    const username = usernameInput.value;
    console.log(username);

    const data = {
      service_id: 'service_798mtu5',
      template_id: 'template_cgo8ucx',
      user_id: '-7pvU3I_b0BG5G-rg',
      template_params: {
        'name': username,
      }
    };

    try {
      const res = await fetch('https://api.emailjs.com/api/v1.0/email/send', {
          method: 'POST',
          body: JSON.stringify(data),
          headers: {
              'Content-Type': 'application/json',
              'Accept': 'application/json'
          }
      });
      console.log("certo: ", res);
    } catch (err) {
      console.log("erro: ", err.stack);
    }
  }

  btnSendEmail.addEventListener('click', sendEmail);
});
