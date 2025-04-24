import { getAllCountries, getCountryDetails } from '../api';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

describe('API Service', () => {
  let mockAxios;

  beforeEach(() => {
    mockAxios = new MockAdapter(axios);
  });

  afterEach(() => {
    mockAxios.restore();
  });

  describe('getAllCountries', () => {
    it('should return countries data on successful request', async () => {
      const mockData = [{ name: 'Testland', flag: 'test-flag.png' }];
      mockAxios.onGet('http://localhost:8080/countries').reply(200, mockData);

      const result = await getAllCountries();
      expect(result).toEqual(mockData);
    });

    it('should handle errors gracefully', async () => {
      mockAxios.onGet('http://localhost:8080/countries').reply(500);

      const result = await getAllCountries();
      expect(result).toEqual([]);
    });
  });

  describe('getCountryDetails', () => {
    it('should return country details for a valid name', async () => {
      const mockData = { name: 'Testland', capital: 'Testville', population: 1000000 };
      mockAxios.onGet('http://localhost:8080/countries/Testland').reply(200, mockData);

      const result = await getCountryDetails('Testland');
      expect(result).toEqual(mockData);
    });

    it('should return null for invalid country', async () => {
      mockAxios.onGet('http://localhost:8080/countries/Invalid').reply(404);

      const result = await getCountryDetails('Invalid');
      expect(result).toBeNull();
    });
  });
});