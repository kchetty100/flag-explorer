module.exports = {
  testEnvironment: 'jsdom',
  setupFilesAfterEnv: ['<rootDir>/src/setupTests.js'],
  transform: {
    '^.+\\.(js|jsx)$': 'babel-jest',
  },
  transformIgnorePatterns: ['/node_modules/(?!(axios|react-router-dom)/)'],
  moduleNameMapper: {
    '^axios$': require.resolve('axios'),
  },
};
