# INTRO (JUDY)

## Genre and Commonalities/Differences (NOAH)
### Genre: Physics Games
- Games: Mini Golf, Air Hockey, Arena (Head) Soccer, Pinball, Shuffle Boarding
- Differences:
    - Turn-based and live games
    - Single player vs multiplayer
    - Win conditions (rounds completed vs threshold)
- Similarities:
    - All 2 Dimensional Games
    - Concept of active and static states
    - Involves collisions between game objects and friction caused by moving objects colliding with the surface

## Planned Features (JUDY)
- Game Authoring No Code Environment
- Abstracted Away Physics Engine
- Optional Features:
    - Online Gaming
    - Friend circles and leaderboards / social center

## Prioritized Accomplishments for Sprint (ALISHA)
- Balancing 2 Different and Competing Things:
    1. Working single hole mini golf game with different obstacles, adhering to basic laws of physics
    2. Abstractions to make it extendable for future sprints
- Focus on game engine, assuming files were already created by the authoring environment
- Implementing Design Patterns for easier project extension

## Gitlab Sprint 1 Milestone Overview (JUDY)
- Most of the mini golf features completed
- Basic parts of the leaderboard not completed
- Struggled with fleshing out APIs and integration at the end impeded progress

## Program Demonstration (NOAH)
- Highlight mini golf gameplay:
    1. Ball bouncing off
    2. Ball hitting water
    3. Differences between surfaces (e.g., green vs rough)
    4. Ball hitting the hole

## Data Files and Usage (ALISHA)
- SinglePlayerMiniGolf file:
    - Highlight collidables and their properties
    - Show rules (win conditions, collisions) mapped to Command classes and TurnPolicy

## Testing (Noah)
- Unfortunately, no time for javafx test during this sprint, but had working model tests. 
  The way we did collision detection made it difficult, so we will need to change this in the 
  future.

## Lessons Learned and Next Sprint Plan
### Positive Event (JUDY)
- **Positive:** Last night, high morale and teamwork prevailed, despite longer integration time. Effective collaboration and support among team members.
- **Learned:** Importance of collective effort and support, fostering team spirit.

### Issue Event (JUDY)
- **Issue:** Miscommunication of Parser Responsibilities led to redundant efforts and confusion.
- **Learned:** Importance of clear communication, delegation, and regular stand-up meetings.

### Teamwork and Communication Reflection (ALISHA)
#### What Worked:
- Effective use of group and individual chats.
- Whiteboarding APIs proved helpful at the start.

#### What Didn't Work:
- Changes to APIs without communication caused merge conflicts.
- Backlog utilization could have been better.

#### Improvement Plan:
- Be diligent in using and updating the backlog.
- Implement smaller scoped issues and feature branches.
- Regularly sync up with the main branch.

## Next Sprint Plan (NOAH)
- Focus on authoring environment.
- Refactoring on the model side, adding a new physics engine for collision detection.
- Potentially adding another game, introducing multiplayer concepts.
- Enhance view side with more animations for pre-turn, as seen in mini golf.
