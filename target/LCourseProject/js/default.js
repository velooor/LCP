function refreshProtection(event) {
    if (event.key === "F5"
        || event.keyCode === 116) {
        event.preventDefault();
        event.stopPropagation();
    }
}
window.addEventListener("keydown", refreshProtection);
