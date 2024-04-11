# Noah Loewy

## Friction
- \( v'_{1y} = v_{1y} - g \cdot \mu_2 \cdot dt \cdot \frac{v_{1y}}{\mid \mid v_1 \mid \mid} \)
- \( \Delta = - g \cdot \mu_2 \cdot dt \cdot \frac{v_{1y}}{\mid \mid v_1 \mid \mid} \)

## Momentum
- \( v'_{1y} = \frac{{2 \cdot m_2 \cdot v_{2y} + v_{1y} \cdot (m_1 - m_2)}} { m_1 + m_2 } \)
- \( \Delta = \frac{{2 \cdot m_2 \cdot v_{2y} + v_{1y} \cdot (m_1 - m_2)}} { m_1 + m_2 } - v_{1y} \)

### Friction

#### Both Moveables (Both \( \mu \) is 0)
- \( v'_{1y} = v_{1y} - g \cdot \mu_2 \cdot dt \cdot \frac{v_{1y}}{\mid \mid v_1 \mid \mid} \)
- \( v'_{1y} = v_{1y} - g \cdot 0 \cdot dt \cdot \frac{v_{1y}}{\mid \mid v_1 \mid \mid} \)
- \( v'_{1y} = v_{1y} \rightarrow \Delta = 0\)

#### Both Surfaces (both speed is 0)
- \( v'_{1y} = v_{1y} - g \cdot \mu_2 \cdot dt \cdot \frac{v_{1y}}{\mid \mid v_1 \mid \mid} \)
- \( v'_{1y} = v_{1y} - g \cdot \mu_2 \cdot dt \cdot \frac{0}{\mid \mid v_1 \mid \mid} \)
- \( v'_{1y} = v_{1y} \rightarrow \Delta = 0\)

#### 1 Moveable, 2 Surface (1 \( \mu \) is 0, 2 speed is 0)
- \( v'_{1y} = v_{1y} - g \cdot \mu_2 \cdot dt \cdot \frac{v_{1y}}{\mid \mid v_1 \mid \mid} \rightarrow \Delta = - g \cdot \mu_2 \cdot dt \cdot \frac{v_{1y}}{\mid \mid v_1 \mid \mid}\)

#### 1 Surface, 2 Moveable (1 speed is 0, 2 \( \mu \) is 0)
- \( v'_{1y} = v_{1y} - g \cdot \mu_2 \cdot dt \cdot \frac{v_{1y}}{\mid \mid v_1 \mid \mid} \)
- \( v'_{1y} = v_{1y} - g \cdot 0 \cdot dt \cdot \frac{0}{\mid \mid v_1 \mid \mid} \)
- \( v'_{1y} = v_{1y} \rightarrow \Delta = 0\)

### Momentum

#### Both Moveables (Both \( \mu \) is 0)
- \(v'_{1y} = \frac{{(2 \cdot m_2 \cdot v_{2y}) + (v_{1y} \cdot m_1) - (v_{1y} \cdot m_2)}} { m_1 + m_2 }\)
- \( \Delta = \frac{{(2 \cdot m_2 \cdot v_{2y}) + (v_{1y} \cdot m_1) - (v_{1y} \cdot m_2)}} { m_1 + m_2 } - v_{1y}\)

#### Both Surfaces (Both speed is 0, Inf mass)
- \(v'_{1y} = \frac{{(2 \cdot m_2 \cdot v_{2y}) + (v_{1y} \cdot m_1) - (v_{1y} \cdot m_2)}} { 
  m_1 + m_2 }\)
- \(v'_{1y} = \frac{{(2 \cdot \infty \cdot 0) + (0 \cdot \infty) - (0 \cdot m_2)}} {\infty + \infty }\)
- \( v'_{1y} = 0 \rightarrow \Delta = 0\)

#### 1 Moveable, 2 Surface (inf mass, 0 speed)
- \(v'_{1y} = \frac{{(2 \cdot m_2 \cdot v_{2y}) + (v_{1y} \cdot m_1) - (v_{1y} \cdot m_2)}} { m_1 + m_2 }\)
- \(v'_{1y} = \frac{{(2 \cdot \infty \cdot 0) + (v_{1y} \cdot m_1) - (v_{1y} \cdot \infty)}} { m_1 + \infty }\)
- \(v'_{1y} = \frac{v_{1y} \cdot (m_1 - \infty)} { m_1 + \infty }\)
- \( \Delta = \frac{v_{1y} \cdot (m_1 - \infty)} { m_1 + \infty } - v_{1y}\)
- \( \Delta = \frac{v_{1y} \cdot m_1 - v_{1y} \cdot \infty} { m_1 + \infty } - \frac{v_{1y} \cdot m_1 + v_{1y} \cdot \infty} { m_1 + \infty } \)
- \( \Delta = \frac{v_{1y} ( m_1 - \infty)} { m_1 + \infty } - \frac{v_{1y} ( m_1 + \infty)} { m_1 + \infty } \)

#### 1 Surface (inf mass, speed 0), 2 Moveable
- \(v'_{1y} = \frac{{(2 \cdot m_2 \cdot v_{2y}) + (v_{1y} \cdot m_1) - (v_{1y} \cdot m_2)}} { m_1 + m_2 }\)
- \(v'_{1y} = \frac{{(2 \cdot m_2 \cdot v_{2y}) + (0 \cdot \infty) - (0 \cdot m_2)}} { \infty + m_2 }\)
- \( v'_{1y} = 0 \
