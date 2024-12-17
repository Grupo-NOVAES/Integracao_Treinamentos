document.addEventListener("DOMContentLoaded", function () {
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
});
