import React, { useState, useEffect } from 'react';
import { getAllCountries } from '../services/api';
import CountryGrid from '../components/CountryGrid';

const HomePage = () => {
  const [countries, setCountries] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCountries = async () => {
      const data = await getAllCountries();
      setCountries(data);
      setLoading(false);
    };

    fetchCountries();
  }, []);

  if (loading) return <div>Loading countries...</div>;

  return (
    <div className="home-page">
      <h1>Flag Explorer</h1>
      <CountryGrid countries={countries} />
    </div>
  );
};

export default HomePage;