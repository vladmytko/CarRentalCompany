import axios from "axios";
import {jwtDecode} from "jwt-decode";

// Global interceptor to add token automatically
axios.interceptors.request.use(
    (config) => {
        const isPublic = config.url.includes("/public") || config.url.includes("/auth");
        if (!isPublic) {
            const token = localStorage.getItem("token");
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default class ApiService {

    static BASE_URL = "http://localhost:4040"

    static getHeader() {
        const token = localStorage.getItem("token");
        return {
            Authorization: token ? `Bearer ${token}`: "", // Ensure token is not null
            "Content-Type": "application/json"
        };
    }

    static getDecodedToken() {
        const token = localStorage.getItem('token'); // Retrieves the JWT from local storage
        if (!token) return null; // if no token, return null
        
        try {
            return jwtDecode(token); // Attemots to decode the JWT
        } catch (err) {
            console.error("Invalid JWT", err); // Catches decoding errors 
            return null; // Returns null if decoding fails
        }
    }























     /** ====================================================   PAYLOAD VALIDATION   ===========================================================*/


      /**
     * Helper function to validate inputs before making PUT or PATCH requests.
     * Ensures no null or undefined values are sent.
     */

      static validatePayload(payload) {
        if(Object.values(payload).some(value => value === null || value === undefined)) {
            console.error("Validation Error: Some required fields are missing.");
            throw new Error("Validation Error: All required fields must be provided.");
        }
    }

    /**
     * Validates car details, used in addCar method. 
     * Create for ADD requiest. Check if fields not null, undefined or empty.
     */
    static validateCarPayload(payload) {
        const requiredFields = ['make', 'model', 'registrationNumber', 'carType', 'numberOfSeats', 'price'];

        for (const field of requiredFields) {
            if (payload[field] === null || payload[field] === undefined || payload[field] === '') {
                console.error(`Validation Error: '${field}' is required.`);
                throw new Error (`Validation Error: '${field}' is required`);
            }
        }

        if (payload.type === 'VAN' && (payload.loadCapacity === null || payload.loadCapacity === undefined)){
            console.error("Validation Error: 'loadCapacity' is required for vans");
            throw new Error("Validation Error: 'loadCapacity' is required for vans");
        }
    }

    /**
     *  Method to validate Car Update Request details for updating car details.
     *  Create for PATCH requiest. Check if fields not null, undefined or empty.
     *  Also checks if carId is present.
    */
    static validateUpdateCarPayload(payload) {
        const allowedFields = ['carId', 'make', 'model', 'registrationNumber', 'carType', 'numberOfSeats', 'carPrice', 'carAvailability'];

        for (const field in payload) {
            if (allowedFields.includes(field)) {
                const value = payload[field];

                if (value === null || value === undefined || value === '') {
                    console.error(`Validation Error: '${field}' cannot be null, undefined, or empty.`);
                    throw new Error(`Validation Error: '${field}' cannot be null, undefined, or empty.`);
                }
            }
        }

        if(!payload.carId){
            throw new Error("Validation Error: 'carId' is required for updating a car.");
        }
    }

    /**
     * Validates Login Payload.
     * Checks email and password. 
     * Ensures no null or undefined values are sent.
     */
    static validateLoginPayload(payload) {
        const requiredFields = ['email', 'password'];
        for (const field of requiredFields) {
            if (!payload[field] || payload[field].trim() === '' || payload[field] === undefined) {
                throw new Error(`Validation Error: '${field}' is required.`)
            }
        }
    }

    /**
     * Validates Register Payload.
     * Checks email, password, name, phoneNumber and dateOfBith.
     * Ensures no null or undefined values are sent.
     */
    static validateRegisterPayload(payload) {
        const requiredFields = ['email', 'password','name','phoneNumber','dateOfBirth'];
        for (const field of requiredFields) {
            if (!payload[field] || payload[field].trim() === '' || payload[field] === undefined) {
                throw new Error(`Validation Error: '${field}' is required.`)
            }
        }
    }

    /**
     * Validates Booking Payload.
     * Checks email, password, name, phoneNumber and dateOfBith.
     * Ensures no null or undefined values are sent.
     */
    static validateBookingPayload(payload) {
        const requiredFields = ['checkInDate', 'checkOutDate','numOfAdults','numOfChildren'];
        for (const field of requiredFields) {
            if (!payload[field] || payload[field].trim() === '' || payload[field] === undefined) {
                throw new Error(`Validation Error: '${field}' is required.`)
            }
        }
    }



    




















    /** ====================================================   AUTH   ===========================================================*/

    /* Register new user */
    static async registerUser(registration) {

        this.validateRegisterPayload(registration);

        try {
            const response = await axios.post(`${this.BASE_URL}/auth/register`, registration);
            return response.data;
        } catch (error) {
            console.error("Registration Error:", error.response?.data || error.message);
            throw error;
        }
    }

    /* Login */
    static async loginUser(loginDetails) {

        this.validateLoginPayload(loginDetails);

        try{
            const response = await axios.post(`${this.BASE_URL}/auth/login`, loginDetails);
            return response.data;
        } catch (error) {
            console.error("Login Error:", error.response?.data || error.message);
            throw error;
        }
    }

















     /** ====================================================  AUTHENTICATION CHECKER   ====================================================*/

     static isAuthenticated() {
        const decoded = this.getDecodedToken();
        if (!decoded) return false;
    
        const currentTime = Date.now() / 1000; // seconds
        return decoded.exp && decoded.exp > currentTime;
    }

        
    static logout() {
        localStorage.removeItem('token');
    }
    
    
    static isAdmin() {
        const decoded = this.getDecodedToken();
        return decoded?.role === 'ADMIN';
    }
    
    static isUser() {
        const decoded = this.getDecodedToken();
        return decoded?.role === 'USER';
    }


















    /** ====================================================   USER FUNCTIONS   ====================================================*/

    /* Get all users (ADMIN) */
    static async getAllUsers(){
        try{
            const response = await axios.get(`${this.BASE_URL}/users/get-all-users`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting all users", error.response?.data || error.message);
            throw error;
        }
    }


    /* Get user by ID (ADMIN) */
    static async getUserById(userId) {
        try{
            const response = await axios.get(`${this.BASE_URL}/users/get-user-by-id/${userId}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting user by ID", error.response?.data || error.message );
        }
    }

    /* Get user by Name (ADMIN) */
    static async getUserByName(name) {
        try{
            const response = await axios.get(`${this.BASE_URL}/users/get-user-by-name/${name}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting user by name", error.response?.data || error.message );
        }
    }

     /* Get user by Email (ADMIN) */
     static async getUserByEmail(email) {
        try{
            const response = await axios.get(`${this.BASE_URL}/users/get-user-by-email/${email}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting user by email", error.response?.data || error.message );
        }
    }

    /* Get user by Email (ADMIN) */
    static async getUserBookingHisotry(userId) {
        try{
            const response = await axios.get(`${this.BASE_URL}/users/get-user-booking-history/${userId}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting user booking history", error.response?.data || error.message );
        }
    }



    /* Delete user by ID (ADMIN) */
    static async deleteUser(userId) {
        try{
            const response = await axios.delete(`${this.BASE_URL}/users/delete-user-by-id/${userId}`,{
                headers: this.getHeader()
            });
            return response;
        } catch (error) {
            console.error("Error deleteing user by ID", error.response?.data || error.message );
        }
    }

    /* Update user details (ADMIN or STAFF) */
    static async updateUserDetails(userId, userData) {

        this.validatePayload({...userData, userId});

        try {
            const response = await axios.patch(`${this.BASE_URL}/users/update-user-by-id/${userId}`,
                userData, // Sending as request body
               {headers: this.getHeader()}
            );
            return response.data;
        } catch (error) {
            console.error("Error updating user details by Id", error.response?.data || error.message);
            throw error;
        }
    }

    /* Update self user details (PATIENT) */
    static async updateSelfUserDetails(userData) {
        this.validatePayload(userData);

        try{
            const response = await axios.patch(`${this.BASE_URL}/users/update-self-user`,
                userData,
                {headers:this.getHeader()}
            );
            return response.data;
        } catch (error) {
            console.error("Error updating self user details", error.response?.data || error.message);
            throw error;
        }
    }





















    /** ====================================================   CAR FUNCTIONS   ====================================================*/

    static async AddCar(carDetails) {

        this.validateCarPayload(carDetails);

        const formData = new FormData();
        formData.append('make', carDetails.make);
        formData.append('model', carDetails.model);
        formData.append('registrationNumber', carDetails.registrationNumber);
        formData.append('carType', carDetails.carType);
        formData.append('numberOfSeats', carDetails.numberOfSeats);
        formData.append('price', carDetails.price);

        if(carDetails.type === 'VAN'){
            formData.append('loadCapacity', carDetails.loadCapacity);
        }

        if(carDetails.image) {
            formData.append('image', carDetails.image);
        }
        
        try {
            const response = await axios.post(`${this.BASE_URL}/cars/add-car`, formData);
            return response.data;
        } catch (error) {
            console.error("Add Car Error:", error.response?.data || error.message);
            throw error;
        }
    }

    /* Get car by ID */
    static async getCarById(carId) {
        try{
            const response = await axios.get(`${this.BASE_URL}/cars/public/get-car-by-id/${carId}`);
            return response.data;
        } catch (error) {
            console.error("Error getting car by ID", error.response?.data || error.message );
            throw error;
        }
    }

    /* Get all cars (ADMIN) */
    static async getAllCars() {
        try{
            const response = await axios.get(`${this.BASE_URL}/cars/get-all-cars`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting all cars", error.response?.data || error.message );
            throw error;
        }
    }

    // /* Get all available cars (ADMIN and User) */
    // static async getAllAvailableCars() {
    //     try{
    //         const response = await axios.get(`${this.BASE_URL}/cars/get-all-available-cars`,{
    //             headers: this.getHeader()
    //         });
    //         return response.data;
    //     } catch (error) {
    //         console.error("Error getting all available cars", error.response?.data || error.message );
    //         throw error;
    //     }
    // }

    /* Get all available cars (ADMIN and User) */
    static async getAllAvailableCars() {
        try{
            const response = await axios.get(`${this.BASE_URL}/cars/public/get-all-available-cars`);
            return response.data;
        } catch (error) {
            console.error("Error getting all available cars", error.response?.data || error.message );
            throw error;
        }
    }

    /* Get all rented cars (ADMIN) */
    static async getAllRentedCars() {
        try{
            const response = await axios.get(`${this.BASE_URL}/cars/get-all-rented-cars`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting all rented cars", error.response?.data || error.message );
            throw error;
        }
    }

    /* Get all cars on Maintenance (ADMIN) */
    static async getAllMaintenanceCars() {
        try{
            const response = await axios.get(`${this.BASE_URL}/cars/get-all-maintenance-cars`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting all cars on maintenance", error.response?.data || error.message );
            throw error;
        }
    }

    /* Get all car types (ADMIN and User) */
    static async getAllCarTypes() {
        try{
            const response = await axios.get(`${this.BASE_URL}/cars/types`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting car types", error.response?.data || error.message );
            throw error;
        }
    }

    /* Delete car by ID (ADMIN) */
    static async delteCarById(carId) {
        try{
            const response = await axios.delete(`${this.BASE_URL}/cars/delete-car-by-id/${carId}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error deleting car by id", error.response?.data || error.message );
            throw error;
        }
    }

    /* Delete car by ID (ADMIN) */
    static async updateCarByCarId(carId, updateCarData) {

        this.validateUpdateCarPayload({...updateCarData, carId});

        try{
            const response = await axios.patch(`${this.BASE_URL}/cars/update-car/${carId}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error deleting car by id", error.response?.data || error.message );
            throw error;
        }
    }
















    /** ====================================================   BOOKING FUNCTIONS   ====================================================*/

    /** Create Booking (ADMIN and USER) */
    static async bookCar(carId, bookingDetails){
        
        this.validateBookingPayload(bookingDetails);
        try {
            const response = await axios.post(`${this.BASE_URL}/booking/book-car/${carId}`,
                bookingDetails,
                {headers: this.getHeader()}
            );
            return response.data;
        } catch (error) {
            console.error("Error booking car: ", error.regispone?.data || error.message);
            throw error;
        }
    }

    /* Delete booking by Confirmation Code (ADMIN and User) */
    static async deteteBookingByCode(confirmationCode) {
        try{
            const response = await axios.delete(`${this.BASE_URL}/booking/delete-booking/${confirmationCode}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error deleting booking by confirmationCode", error.response?.data || error.message );
            throw error;
        }
    }

    /* Get booking by confirmation code (ADMIN and User) */
    static async getBookingByConfirmationCode(confirmationCode) {
        try{
            const response = await axios.get(`${this.BASE_URL}/booking/get-booking-by-confirmation-code/${confirmationCode}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting booking by confirmationCode", error.response?.data || error.message );
            throw error;
        }
    }

    /* Get booking by user ID (ADMIN) */
    static async getBookingByUserId(userId) {
        try{
            const response = await axios.get(`${this.BASE_URL}/booking/get-booking-by-user-id/${userId}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting booking by user ID", error.response?.data || error.message );
            throw error;
        }
    }

    /* Get booking by car ID (ADMIN) */
    static async getBookingByCarId(carId) {
        try{
            const response = await axios.get(`${this.BASE_URL}/booking/get-booking-by-car-id/${carId}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting booking by car ID", error.response?.data || error.message );
            throw error;
        }
    }


     /* Get user booking automatecally (User) */
     static async getUserBooking() {
        try{
            const response = await axios.get(`${this.BASE_URL}/booking//get-user-booking`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting user bookings ", error.response?.data || error.message );
            throw error;
        }
    }

     /* Update booking (User) */
    //  static async updateBooking() {
    //     try{
    //         const response = await axios.get(`${this.BASE_URL}/booking//get-user-booking`,{
    //             headers: this.getHeader()
    //         });
    //         return response.data;
    //     } catch (error) {
    //         console.error("Error getting user bookings ", error.response?.data || error.message );
    //         throw error;
    //     }
    // }
}



