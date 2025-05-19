import React from "react";
import "../../styles/find-car-form.css";
import "../../styles/find-car-form.css";
import { Form, FormGroup } from "reactstrap";

const FindCarForm = () => {
  return (
    <Form className="form">
      <div className=" d-flex align-items-center justify-content-between flex-wrap">
        <FormGroup className="form__group">
          <h5 className="text-dark ms-1">Pickup location</h5>
          <input type="text" placeholder="Stansted Airport" required />
        </FormGroup>

        <FormGroup className="form__group">
          <h5 className="text-dark ms-1">Pickup date</h5>
          <input type="date" placeholder="Pickup date" required />
        </FormGroup>

        <FormGroup className="form__group">
          <h5 className="text-dark ms-1">Pickup time</h5>
          <input
            className="journey__time"
            type="time"
            placeholder="Pickup time"
            required
          />
        </FormGroup>

        <FormGroup className="form__group">
          <h5 className="text-dark ms-1">Return location</h5>
          <input type="text" placeholder="Stansted Airport" required />
        </FormGroup>

         <FormGroup className="form__group">
          <h5 className="text-dark ms-1">Return date</h5>
          <input type="date" placeholder="Return date" required />
        </FormGroup>

        <FormGroup className="form__group">
          <h5 className="text-dark ms-1">Return time</h5>
          <input
            className="journey__time"
            type="time"
            placeholder="Return time"
            required
          />
        </FormGroup>

        <FormGroup className="form__group">
          <button className="btn find__car-btn">Find Car</button>
        </FormGroup>
      </div>
    </Form>
  );
};

export default FindCarForm;
