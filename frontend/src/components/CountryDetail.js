import React from 'react';
import { useNavigate } from 'react-router-dom';

const CountryDetail = ({ country }) => {
  const navigate = useNavigate();

  return (
    <div className="country-detail">
      <button onClick={() => navigate(-1)}>Back</button>
      <h1>{country.name}</h1>
      <img src={country.flag} alt={`Flag of ${country.name}`} />
      <div className="details">
        <p><strong>Capital:</strong> {country.capital}</p>
        <p><strong>Population:</strong> {country.population.toLocaleString()}</p>
      </div>
    </div>
  );
};

export default CountryDetail;