// Handle banner image update based on theme
const bannerImage = document.getElementById("bannerImage");

function updateBannerImage(theme) {
    if (!bannerImage) return; // runs only on pages that contain the image

    if (theme === "dark") {
        bannerImage.src = "assets/images/t1.png";   // Moon mode
    } else {
        bannerImage.src = "assets/images/t2.png";   // Sun mode
    }
}

const toggleSwitch = document.querySelector('.theme-switch input[type="checkbox"]');
const currentTheme = localStorage.getItem('theme');

if (currentTheme) {
    document.documentElement.setAttribute('data-theme', currentTheme);
  
    if (currentTheme === 'dark') {
        toggleSwitch.checked = true;
    }
    updateBannerImage(currentTheme);
}

function switchTheme(e) {
    if (e.target.checked) {
        document.documentElement.setAttribute('data-theme', 'dark');
        localStorage.setItem('theme', 'dark');
        updateBannerImage('dark');
    } else {
        document.documentElement.setAttribute('data-theme', 'light');
        localStorage.setItem('theme', 'light');
        updateBannerImage('light');
    }    
}

toggleSwitch.addEventListener('change', switchTheme, false);