import React, {useEffect, useState} from "react";
import { Col } from "reactstrap";
import { Link } from "react-router-dom";
import "../../styles/car-item.css";
import ApiService from "../../service/ApiService.js";

const CarItem = () => {
  
  const [cars, setCars] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCars = async () => {
      try {
        
        const data = await ApiService.getAllAvailableCars();
        console.log("Response from backend:",data); // Optional debug log
        setCars(data.carList); // Only set car list

      } catch (err) {
        setError(err.message || "Failed to fetch cars.");
      }
    };

    fetchCars();
  }, []);

  return (
    <>
      {error ? (
        <p style={{ color: "red"}}>{error}</p>
      ) : cars.length === 0 ? (
        <p>No cars availabe.</p>
      ) : (
        cars.map((item) => {
          const { 
            carId, 
            make, 
            model, 
            numberOfSeats, 
            carPrice, 
            carPhotoUrl, 
            carType  } = item;
          
          return (
            <Col lg="4" md="4" sm="6" className="mb-5" key={carId}>
            <div className="car__item">
              <div className="car__img">
                <img src={carPhotoUrl} alt="" className="w-100" />
              </div>
      
              <div className="car__item-content mt-4">
                <h4 className="section__title text-center">{make}</h4>
                <h6 className="rent__price text-center mt-">
                  ${carPrice}.00 <span>/ Day</span>
                </h6>
      
                <div className="car__item-info d-flex align-items-center justify-content-between mt-3 mb-4">
                  <span className=" d-flex align-items-center gap-1">
                    <i class="ri-car-line"></i> {model}
                  </span>
                  <span className=" d-flex align-items-center gap-1">
                    <i class="ri-settings-2-line"></i> {make}
                  </span>
                  <span className=" d-flex align-items-center gap-1">
                    <i class="ri-timer-flash-line"></i> {numberOfSeats}
                  </span>
                </div>
      
                <button className=" w-50 car__item-btn car__btn-rent">
                  <Link to={`/cars/${carId}`}>Rent</Link>
                </button>
      
                {/* <button className=" w-50 car__item-btn car__btn-details">
                  <Link to={`/cars/${carId}`}>Details</Link>
                </button> */}

                <Link to={`/cars/${carId}`} className="w-50 car__item-btn car__btn-rent">
                  Rent
                </Link>
                
              </div>
            </div>
          </Col>
          )
        })
      )}
    </>
  );
};

export default CarItem;
