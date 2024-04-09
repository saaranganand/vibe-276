for (let i = 0; i < 10; i++) {
    let note = document.createElement('div');
    note.style.position = 'fixed';
    note.style.width = '45px';
    note.style.height = '50px';
    note.style.backgroundImage = "url('/img/assets/music-note.png')";
    note.style.backgroundSize = 'contain';
    note.style.opacity = '1';
    note.style.zIndex = '-1';

    let edge = Math.floor(Math.random() * 4);
    let angle = Math.random() * Math.PI / 2; // random angle in radians
    let translateX, translateY;
    let animationName;
    switch (edge) {
        case 0: // top edge
            note.style.top = '0';
            note.style.left = `${Math.random() * window.innerWidth}px`;
            animationName = `floatNoteDown${i}`;
            translateX = Math.tan(angle) * 100;
            translateY = 100;
            break;
        case 1: // right edge
            note.style.top = `${Math.random() * window.innerHeight}px`;
            note.style.left = `${window.innerWidth - 45}px`; // subtract the width of the note
            animationName = `floatNoteLeft${i}`;
            translateX = -100;
            translateY = Math.tan(angle) * 100;
            break;
        case 2: // bottom edge
            note.style.top = `${window.innerHeight - 50}px`; // subtract the height of the note
            note.style.left = `${Math.random() * window.innerWidth}px`;
            animationName = `floatNoteUp${i}`;
            translateX = Math.tan(angle) * 100;
            translateY = -100;
            break;
        case 3: // left edge
            note.style.top = `${Math.random() * window.innerHeight}px`;
            note.style.left = '0';
            animationName = `floatNoteRight${i}`;
            translateX = 100;
            translateY = Math.tan(angle) * 100;
            break;
    }

    let style = document.createElement('style');
    style.innerHTML = `
        @keyframes ${animationName} {
            0% { transform: translate(0, 0); }
            100% { transform: translate(${translateX}vw, ${translateY}vh); }
        }
    `;
    document.head.appendChild(style);

    note.style.animation = `${animationName} ${Math.random() * 5 + 5}s linear infinite, fade ${Math.random() * 5 + 5}s linear infinite`;
    document.body.appendChild(note);
}