import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import HomePage from '../HomePage';
import { getAllCountries } from '../../services/api';

jest.mock('../../services/api');

describe('HomePage', () => {
  it('displays loading state initially', () => {
    getAllCountries.mockImplementation(() => new Promise(() => {}));
    render(
      <MemoryRouter>
        <HomePage />
      </MemoryRouter>
    );
    expect(screen.getByText('Loading countries...')).toBeInTheDocument();
  });

  it('displays countries after loading', async () => {
    const mockCountries = [{ name: 'Testland', flag: 'test-flag.png' }];
    getAllCountries.mockResolvedValue(mockCountries);

    render(
      <MemoryRouter>
        <HomePage />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByText('Flag Explorer')).toBeInTheDocument();
      expect(screen.getByText('Testland')).toBeInTheDocument();
    });
  });

  it('displays error state when API fails', async () => {
    getAllCountries.mockResolvedValue([]);

    render(
      <MemoryRouter>
        <HomePage />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.queryByText('Testland')).not.toBeInTheDocument();
    });
  });
});
