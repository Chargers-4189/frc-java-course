// @ts-check
// `@type` JSDoc annotations allow editor autocompletion and type checking
// (when paired with `@ts-check`).
// There are various equivalent ways to declare your Docusaurus config.
// See: https://docusaurus.io/docs/api/docusaurus-config

import { themes as prismThemes } from 'prism-react-renderer';

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'Charging Courses',
  tagline: 'FRC Programming Resources & Curriculums',
  favicon: 'img/favicon.ico',

  url: 'https://chargingcourses.com',
  baseUrl: '/',

  organizationName: 'Chargers-4189', // Usually your GitHub org/user name.
  projectName: 'frc-java-course', // Usually your repo name.

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: './sidebars.js',
        },
        blog: {
          showReadingTime: true,
          feedOptions: {
            type: ['rss', 'atom'],
            xslt: true,
          },
          // Useful options to enforce blogging best practices
          onInlineTags: 'warn',
          onInlineAuthors: 'warn',
          onUntruncatedBlogPosts: 'warn',
        },
        theme: {
          customCss: './src/css/custom.css',
        },
      }),
    ],
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      // Replace with your project's social card
      image: 'img/docusaurus-social-card.jpg',
      navbar: {
        title: 'Charging Courses',
        logo: {
          alt: 'Charging Courses Logo',
          src: 'img/CC_Icon_Inv.png',
        },
        items: [
          {
            type: 'docSidebar',
            sidebarId: 'courseSidebar',
            position: 'left',
            label: 'FRC Java Course',
          },
          { to: '/blog', label: 'Blog', position: 'left' },
          {
            href: 'https://github.com/Chargers-4189/frc-java-course',
            label: 'GitHub',
            position: 'right',
          },
        ],
      },
      footer: {
        style: 'dark',
        links: [
          {
            title: 'Articles',
            items: [
              {
                label: 'FRC Lessons',
                to: '/docs/intro',
              },
              {
                label: 'Blog',
                to: '/blog',
              },
            ],
          },
          {
            title: 'Community',
            items: [
              {
                label: 'GitHub',
                href: 'https://github.com/Chargers-4189',
              },
              {
                label: 'Instagram',
                href: 'https://www.instagram.com/team4189/',
              },
              {
                label: 'Twitter',
                href: 'https://twitter.com/team4189',
              },
            ],
          },
          {
            title: 'Contact',
            items: [
              {
                label: 'Email',
                to: 'mailto:cvang07@jeffcityschools.org,jdharriman06@jeffcityschools.org?subject=Charging%20Courses',
              },
              {
                label: 'Website',
                href: 'https://www.team4189.org',
              },
            ],
          },
        ],
        copyright: `Charging Courses by Chargers Team 4189. Built with Docusaurus.`,
      },
      prism: {
        theme: prismThemes.github,
        darkTheme: prismThemes.dracula,
      },
      liveCodeBlock: {
        playgroundPosition: 'bottom',
      },
      themes: ['@docusaurus/theme-mermaid'],
      markdown: {
        mermaid: true,
      },
    }),
  plugins: [
    '@docusaurus/theme-live-codeblock',
    [
      '@docusaurus/plugin-pwa',
      {
        debug: true,
        offlineModeActivationStrategies: [
          'appInstalled',
          'standalone',
          'queryString',
        ],
        pwaHead: [
          {
            tagName: 'link',
            rel: 'icon',
            href: '/img/CC_Icon_Inv.png',
          },
          {
            tagName: 'link',
            rel: 'manifest',
            href: '/manifest.json',
          },
          {
            tagName: 'meta',
            name: 'theme-color',
            content: 'rgb(37, 194, 160)',
          },
        ],
      },
    ],
  ],
};

export default config;
