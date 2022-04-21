let formBtn = document.querySelector('#create-btn');
let creForm = document.querySelector('.center');
let formClose = document.querySelector('#form-close');
let formBtn2 = document.querySelector('#join-btn');
let joinForm = document.querySelector('.center2');
let formClose2 = document.querySelector('#form-close2');

formBtn.addEventListener('click', () => {
    creForm.classList.add('active');
});

formClose.addEventListener('click', () => {
   creForm.classList.remove('active');
});

formBtn2.addEventListener('click', () => {
    joinForm.classList.add('active');
});

formClose2.addEventListener('click', () => {
    joinForm.classList.remove('active');
});