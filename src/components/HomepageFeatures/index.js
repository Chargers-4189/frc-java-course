import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

const FeatureList = [
  {
    title: 'Structured Course',
    Svg: require('@site/static/img/FT-structured-course.svg').default,
    description: (
      <>
        We use the Experience First, Formalize Later model to invoke
        collaborative thinking and discussions of programming concepts.
      </>
    ),
  },
  {
    title: 'Project-Based',
    Svg: require('@site/static/img/FT-project-based.svg').default,
    description: (
      <>
        We believe that project-based learning is important for understanding
        FRC concepts, so we incorporate many robot and vision applications.
      </>
    ),
  },
  {
    title: 'For Beginners',
    Svg: require('@site/static/img/FT-for-beginners.svg').default,
    description: (
      <>
        Whether you're a seasoned Java programmer or haven't touched code, this
        course will guide you through the necessary steps to master FRC
        programming.
      </>
    ),
  },
];

function Feature({ Svg, title, description }) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <Svg className={styles.featureSvg} role="img" />
      </div>
      <div className="text--center padding-horiz--md">
        <Heading as="h3">{title}</Heading>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures() {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
