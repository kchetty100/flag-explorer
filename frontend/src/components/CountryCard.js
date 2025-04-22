import React from 'react';
import { useNavigate } from 'react-router-dom';

const CountryCard = ({ country }) => {
  const navigate = useNavigate();

  return (
    <div
      className="country-card"
      onClick={() => navigate(`/country/${country.name}`)}
    >
      <img src={country.flag} alt={`Flag of ${country.name}`} />
      <h3>{country.name}</h3>
    </div>
  );
};

export default CountryCard;