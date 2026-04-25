import React from 'react'
import '../.././css/Footer.css'



function Footer() {
    const takim = ["Elif Feyza Şengül", "Muhammed Köseoğlu", "Mustafa Mert Çevik", "Ömer Faruk Kara"];

    return (
        <footer className="footer">
            <div className="footer-content">
                <div className="footer-team">
                    {takim.map((member, index) => (
                        <span key={index} className={`team-member ${index === 0 ? 'gold' : ''}`}>{member}</span>
                    ))}
                </div>
                <div className="footer-bottom">
                    &copy; {new Date().getFullYear()} CityGo - Tüm Hakları Saklıdır.
                </div>
            </div>
        </footer>
    )
}

export default Footer