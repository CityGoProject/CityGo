import React, { useState } from 'react';
import { FaArrowRightArrowLeft } from "react-icons/fa6";
import '../css/Home.css';

function Home() {
    // 81 il arasından başlangıç şehirlerini belirleyelim
   const cities = [
  "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir",
  "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli",
  "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari",
  "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir",
  "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir",
  "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat",
  "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman",
  "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"
];
    const [fromCity, setFromCity] = useState("İstanbul");
    const [toCity, setToCity] = useState("Ankara");

    // Bugünün tarihini ve 1 ay sonrasını hesaplayalım
    const today = new Date().toISOString().split('T')[0];
    const maxDate = new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().split('T')[0];
    
    const [travelDate, setTravelDate] = useState(today);

    const searchTickets = () => {
        alert(`Bilet Aranıyor : ${fromCity} -> ${toCity} | Tarih: ${travelDate}`);
    };

    // İki ok işaretine tıklandığında şehirlerin yerini değiştiren fonksiyon
    const swapCities = () => {
        setFromCity(toCity);
        setToCity(fromCity);
    }

    return (
        <div className='search-div'>
            <div>
                <h3 className= "driverbox">Şehir bileti ara</h3>
            </div>
            <div>
                {/* class isimlerini ellemedim CSS'i bozmamak için */}
                <select value={fromCity} onChange={(e) => setFromCity(e.target.value)} className="from-travel-option">
                    {cities.map((city, index) => (
                        <option key={index} value={city}>{city}</option>
                    ))}
                </select>

                <FaArrowRightArrowLeft 
                    onClick={swapCities} 
                    style={{ fontSize: '25px', marginRight: '10px', marginLeft: '10px', cursor: 'pointer', color: '#555' }} 
                />
                <select value={toCity} onChange={(e) => setToCity(e.target.value)} className="to-travel-option">
                    {cities.map((city, index) => (
                        <option key={index} value={city}>{city}</option>
                    ))}
                </select>
                <input 
                    type="date" 
                    value={travelDate} 
                    min={today} 
                    max={maxDate} 
                    onChange={(e) => setTravelDate(e.target.value)}
                    className="date-input"
                />
                <p className="date-warning">Sadece 1 aylık bilet seçimi yapılabilir.</p>
            </div>
            <div>
                <button onClick={searchTickets} className="button" >Bilet Bul</button>
            </div>
        </div>
    )
}

export default Home;
