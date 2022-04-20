let formBtn = document.querySelector('#create-btn');
let creForm = document.querySelector('.center');
let formClose = document.querySelector('#form-close');

formBtn.addEventListener('click', () => {
    creForm.classList.add('active');
});

formClose.addEventListener('click', () => {
   creForm.classList.remove('active');
});