document.addEventListener('DOMContentLoaded', function () {
  const usernameInput = document.getElementById("username");
  const form = document.getElementById("formResponses");
  const errorMessageContainer = document.getElementById("errorMessageContainer");

  if (errorMessageContainer) {
      const errorMessage = errorMessageContainer.dataset.errorMessage;

      if (errorMessage) {
          Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: errorMessage,
          });
      }
  }

  form.addEventListener('submit', function (event) {
      event.preventDefault();
      sendEmail();
  });

  async function sendEmail() {
      const username = usernameInput.value;

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

          if (res.ok) {
              form.submit();
          } else {
              Swal.fire({
                  icon: 'error',
                  title: 'Erro',
                  text: 'Falha ao enviar o e-mail. Tente novamente.',
              });
          }
      } catch (err) {
          Swal.fire({
              icon: 'error',
              title: 'Erro',
              text: 'Ocorreu um erro ao enviar o e-mail.',
          });
          console.log("Erro ao tentar enviar o e-mail: ", err.stack);
      }
  }
});
