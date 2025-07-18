import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  // Look for test files in the "test" directory, relative to this configuration file.
  testDir: 'test',

  // Run all tests in parallel.
//   fullyParallel: true,

  // Reporter to use
  reporter: 'html',

  use: {
    // Base URL to use in actions like `await page.goto('/')`.
    baseURL: 'http://localhost:3000',

    // Collect trace when retrying the failed test.
    trace: 'on-first-retry',
  },
  // Configure projects for major browsers.
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
});