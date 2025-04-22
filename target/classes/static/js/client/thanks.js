document.addEventListener('DOMContentLoaded', async function () {
    const usernameInput = document.getElementById("username");
  
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
        console.log(JSON.stringify(res));
      } catch (err) {
        console.log("Erro ao tentar enviar o e-mail: ", err.stack);
      }
    }
  );
  