import React from 'react';
import "./styles.scss";
import { ReactComponent as Arronwicon } from "core/assets/images/arrow.svg"


type Props = {
    text: string;
}


const Buttonicon = ({text}: Props) => (

    <div className="d-flex">
        <button className="btn btn-primary btn-icon">
            <h5> {text} </h5>
        </button>
        <div className="btn-icon-content">
            <Arronwicon />
        </div>
    </div>
);


export default Buttonicon;