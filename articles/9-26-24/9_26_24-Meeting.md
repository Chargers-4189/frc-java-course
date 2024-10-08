# September 26th Code Meeting

Led by: Caleb & Bryan

### Discussion Topics

- Finite State Machine concepts and understanding
- Implementing FSM into our existing 2024 Crescendo Code

# Meeting Notes

We started this meeting by looking into teams who have already programmed their robot with the Finite State Machine (FSM) style. This programming style is useful for creating complex behaviors in robots. For example, the use case of automatically targeting the goal while in movement. This scenario would benefit from the use of an FSM as we'll discuss later, but it essentially allows the robot to run multiple tasks at the same time.

### Concepts to understand

#### Command-Based Programming

Command-based programming is the default style that most FRC teams use and WPILib recommends for beginner programmers.

- A programming paradigm (style) where the robot's behavior is defined by a series of commands or actions. These commands are scheduled and executed by a scheduler.
- Use in FRC:
  - Commands can represent specific actions, driving, shooting, intake, or more. The scheduler will determine which commands are active and execute them.

#### Finite State Machine (FSM) Programming

FSM programming is used by more advanced teams that require a complex series of actions, which isn't recommended for beginners.

- A programming paradigm that--in its name--has a finite number of states. The robot can transition from one state to another based on specific input events.
- Use in FRC:
  - FSM can define the robot's behavior. **States** represent a specific action, and the **transitions** between states are **triggered** by sensors.

### Looking at other teams

Bryan looked in the 2974 Walton Robotics Code via [GitHub](https://github.com/WaltonRobotics/Crescendo/tree/main/src/main/java/frc/robot) to get an idea of how to structure and code in FSM. I also looked at 4509 Mechanical Bulls [GitHub](https://github.com/FRC-4509-MechBulls/2024_Crescendo_Bot/tree/main/src/main/java/frc/robot) to see (but admittedly did not comprehend any of it).

## Whiteboard Brainstorming

Now knowing some concepts--and Bryan's knowledge of FSM--we decided to mock up a simple FSM for the climb mechanism of our robot. Here's a detailed overview of how command-based programming works to later compare it to FSM.

### Command-Based

There are three important components of a command-based paradigm. The button, which triggers the command. The command, which controls the subsystem. The subsystem, which interfaces with the hardware. So in this simple example, a button press will schedule a command to be run. The command will then run its code that completes a task we want. The subsystem just takes input from the command and applies it to the hardware, like drivetrains, actuators, or mechanisms. Some things to note:

- Triggered by a button, a command will run at a frequency of 50Hz, or 500 times a second until the task is complete or a condition (e.g. letting go of a button)
- Multiple commands can run simultaneously if they don't use the same resource. Only one command can use a subsystem at a time. This prevents different codes from trying to move a motor with other values.

![][commandbased]

[View in Canva](https://www.canva.com/design/DAGR59-yzyg/jg5Cuz46s-LyudnhX2mD0A/view?utm_content=DAGR59-yzyg&utm_campaign=designshare&utm_medium=link&utm_source=viewer)

#### Example Code

Here is an example of our climb code for the subsystem and command to move it up.

<details>
<summary>DriveClimbUp.java Command (Click to see full file)</summary>

```java
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climb;

public class DriveClimbUp extends Command {
  private Climb climb;
  /** Creates a new DriveClimbUp. */
  public DriveClimbUp(Climb climb) {
    this.climb = climb;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!climb.getRestSensor()) {
      climb.setClimb(-1);
    } else {
      climb.setClimb(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climb.setClimb(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
```

</details>

<details>
<summary>Climb.java Subsystem (Click to see full file)</summary>

```java
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  /** Creates a new Climb. */
  private double climbPower = 0;

  private DigitalInput restPosition = new DigitalInput(5);
  private DigitalInput extendPosition = new DigitalInput(4);
  private WPI_TalonSRX climbMotor = new WPI_TalonSRX(12);

  public Climb() {}

  public void setClimb(double power) {
    // STOP in the direction of sensor if detected
    if (getRestSensor() && power < 0) {
      climbPower = 0;
    } else if (getExtendedSensor() && power > 0) {
      climbPower = 0;
    } else {
      climbPower = power;
    }

    this.climbMotor.set(ControlMode.PercentOutput, climbPower);
  }

  public boolean getRestSensor() {
    return this.restPosition.get();
  }
  public boolean getExtendedSensor() {
    return this.extendPosition.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

```

</details>

<br />
But I'll focus on the Subsystem because we'll delete the command when we change to FSM.

```java
public class Climb extends SubsystemBase {
  /** Creates a new Climb. */
  private double climbPower = 0;

  private DigitalInput restPosition = new DigitalInput(Constants.ClimbConstants.kRestLimitDIO);
  private DigitalInput extendPosition = new DigitalInput(Constants.ClimbConstants.kExtendLimitDIO);
  private WPI_TalonSRX climbMotor = new WPI_TalonSRX(Constants.ClimbConstants.kClimbMotorID);

  public Climb() {}

  public void setClimb(double power) {
    // STOP in the direction of sensor if detected
    if (getRestSensor() && power < 0) {
      climbPower = 0;
    } else if (getExtendedSensor() && power > 0) {
      climbPower = 0;
    } else {
      climbPower = power;
    }

    this.climbMotor.set(ControlMode.PercentOutput, climbPower);
  }

  public boolean getRestSensor() {
    return this.restPosition.get();
  }
  public boolean getExtendedSensor() {
    return this.extendPosition.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
```

Lastly, here is our `RobotContainer.java` which binds all the buttons and adds the triggers.

```java
public class RobotContainer {
    /* Other code was redacted for brevity */

    // Defines the operators controller
    private CommandXboxController m_operatorController = new CommandXboxController(0);

    // Defines the climb subsystem
    private final Climb climb = new Climb();
    // Defines the climb command which uses the climb subsystem
    private DriveClimbUp driveClimbUp = new DriveClimbUp(climb);

    // Configures the Up D-pad of the operators controller to trigger the climb command
    private void configureBindings() {
        m_operatorController.povUp().whileTrue(driveClimbUp);
    }
}
```

### FSM

When brainstorming how we would program the climb system in FSM, we thought of all the possible states of the climber.

1. Idle - Not moving
2. ClimbDown - Moving down
3. ClimbUp - Moving up

Then we decided what **triggers** would activate the **transition** to another state. The trigger would be the up and down buttons on the D-pad. For the transitions, we had to think about the conditions for the state to change.

- Idle -> ClimbDown
  - Button D-pad down will activate the request
  - **AND**
  - Check if the bottom limit switch is not clicked (False)
- Idle -> ClimbUp
  - Button D-pad up will activate the request
  - **AND**
  - Check if the top limit switch is not clicked (False)

For each **transition** request, we must satisfy the condition of button press **AND** the limit switch is not activated. Technically we also needed to think about what would happen if both up and down buttons were pressed on the D-pad. However, since the D-pad cannot have both the up and down buttons pressed simultaneously, we ignored that technicality. But make sure to think of every state and transition that can happen.
<br />
Lastly, we thought of the transitions back to Idle:

- ClimbDown --> Idle
  - Button D-pad down is released
  - **OR**
  - Bottom limit switch clicked (True)
- ClimbUp -> Idle
  - Button D-pad up is released
  - **OR**
  - Top limit switch clicked (True)

Again, for each **transition** request, we must satisfy the condition of the button released **OR** the limit switch is activated.

![][statemachine]

[View in Canva](https://www.canva.com/design/DAGR59-yzyg/jg5Cuz46s-LyudnhX2mD0A/view?utm_content=DAGR59-yzyg&utm_campaign=designshare&utm_medium=link&utm_source=viewer)

## Reprogramming the Climber

### Subsystem

Now that we have a design in mind, we started programming. First, we started in the subsystem and removed the previous code that essentially had a state machine inside.

```java
public class Climb extends SubsystemBase {

  private double climbPower = 0;

  private DigitalInput restPosition = new DigitalInput(5);
  private DigitalInput extendPosition = new DigitalInput(4);
  private WPI_TalonSRX climbMotor = new WPI_TalonSRX(12);

  public Climb() {}

  // public void setClimb(double power) {
  //   // STOP in the direction of sensor if detected
  //   if (getRestSensor() && power < 0) {
  //     climbPower = 0;
  //   } else if (getExtendedSensor() && power > 0) {
  //     climbPower = 0;
  //   } else {
  //     climbPower = power;
  //   }
  //   this.climbMotor.set(ControlMode.PercentOutput, climbPower);
  // }

  public boolean getRestSensor() {
    return this.restPosition.get();
  }
  public boolean getExtendedSensor() {
    return this.extendPosition.get();
  }
}
```

Then we added this method in place of the one removed. This runs an instant command when called upon. The `runOnce(command, subsystem)` requires the command's parameters to run and what subsystem to use.

- For the command, we did a lambda which set the climb motor to the power passed into this method.
- For the subsystem, we used the `this` keyword to refer to the current object we're in (the climb subsystem).

```java
public Command driveClimb(double power) {
    return Commands.runOnce(() -> {
        this.climbMotor.set(ControlMode.PercentOutput, climbPower);
    }, this);
}
```

This will return a command which will instantly run the motor at the specified power.

### Robot Container

Now that we have our subsystem completed, we need to trigger it with the transitions we designed earlier. For this, we need access to two things:

- D-pad button presses
- Limit switch sensors

Since the buttons can only be configured in the `RobotContainer.java`, we'll set up our trigger and transitions there and import the subsystem to access the sensors.
<br />
Let's create a method for the climb state machine so it's less messy.

```java
private void configureClimberTransitions() {

}
```

Then add our triggers along with the condition that needs to be satisfied to transition the states. Make sure we add all the possible transitions we outlined earlier.

```java
private void configureClimberTransitions() {
    Trigger moveUp = new Trigger(() -> climb.getRestSensor() == false && m_operatorController.povUp().getAsBoolean());
    Trigger moveDown = new Trigger(() -> climb.getExtendedSensor() == false && m_operatorController.povDown().getAsBoolean());
    Trigger idleUp = new Trigger(() -> climb.getRestSensor() == true || m_operatorController.povUp().getAsBoolean() == false);
    Trigger idleDown = new Trigger(() -> climb.getExtendedSensor() == true || m_operatorController.povDown().getAsBoolean() == false);
}
```

The naming scheme can be better, but we can up with moveUp/moveDown for transitioning to ClimbUp/ClimbDown and idleUp/idleDown for transitioning to Idle from a previous up/down state.
<br />
Lastly, we have to set the state that it's transitioning to.

```java
moveUp.onTrue(climb.driveClimb(1));
moveDown.onTrue(climb.driveClimb(-1));
idleUp.onTrue(climb.driveClimb(0));
idleDown.onTrue(climb.driveClimb(0));
```

It's hard to see but our state values are implemented with the `driveClimb` method. `1` is the state of ClimbUp, `-1` is the state of ClimbDown, and `0` is the state of Idle.
<br />
Before we finish, we have to define the subsystem so that our triggers can use the sensors and we need to bind the buttons.

```java
public class RobotContainer {
    /* Other code was redacted for brevity */

    private CommandXboxController m_operatorController = new CommandXboxController(0);


    private final Climb climb = new Climb();
    //private DriveClimbUp driveClimbUp = new DriveClimbUp(climb); -> Not using commands here

    private void configureBindings() {
        //m_operatorController.povUp().whileTrue(driveClimbUp); -> Not using commands here
        configureClimberTransitions();
    }

    private void configureClimberTransitions() {
        Trigger moveUp = new Trigger(() -> climb.getRestSensor() == false && m_operatorController.povUp().getAsBoolean());
        Trigger moveDown = new Trigger(() -> climb.getExtendedSensor() == false && m_operatorController.povDown().getAsBoolean());
        Trigger idleUp = new Trigger(() -> climb.getRestSensor() == true || m_operatorController.povUp().getAsBoolean() == false);
        Trigger idleDown = new Trigger(() -> climb.getExtendedSensor() == true || m_operatorController.povDown().getAsBoolean() == false);

        moveUp.onTrue(climb.driveClimb(1));
        moveDown.onTrue(climb.driveClimb(-1));
        idleUp.onTrue(climb.driveClimb(0));
        idleDown.onTrue(climb.driveClimb(0));
    }
}
```

Now we're finished with our first state machine for the climber! 🎉

<details>
<summary>Climb.java subsystem (Click to view full file)</summary>

```java
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  /** Creates a new Climber. */

  //switchDown.onFalse

  private double climbPower = 0;

  private DigitalInput restPosition = new DigitalInput(5);
  private DigitalInput extendPosition = new DigitalInput(4);
  private WPI_TalonSRX climbMotor = new WPI_TalonSRX(12);

  public Climb() {}

  public Command driveClimb(double power) {
    return Commands.runOnce(() -> {
      this.climbMotor.set(ControlMode.PercentOutput, climbPower);
    }, this);
  }

  public boolean getRestSensor() {
    return this.restPosition.get();
  }
  public boolean getExtendedSensor() {
    return this.extendPosition.get();
  }
}

```

</details>
<details>
<summary>RobotContainer.java subsystem (Click to view full file)</summary>

```java
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private CommandXboxController m_operatorController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private Climb climb = new Climb();
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    configureClimberTransitions();
  }

  private void configureClimberTransitions() {
    Trigger moveUp = new Trigger(() -> climb.getRestSensor() == false && m_operatorController.povUp().getAsBoolean());
    Trigger moveDown = new Trigger(() -> climb.getExtendedSensor() == false && m_operatorController.povDown().getAsBoolean());
    Trigger idleUp = new Trigger(() -> climb.getRestSensor() == true || m_operatorController.povUp().getAsBoolean() == false);
    Trigger idleDown = new Trigger(() -> climb.getExtendedSensor() == true || m_operatorController.povDown().getAsBoolean() == false);

    moveUp.onTrue(climb.driveClimb(1));
    moveDown.onTrue(climb.driveClimb(-1));
    idleUp.onTrue(climb.driveClimb(0));
    idleDown.onTrue(climb.driveClimb(0));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Commands.none();
  }
}

```

</details>
<br />

# Meeting Conclusion

We completed our example of an FSM style for a simple system on our robot. There are probably better and easier ways, but this is what we came up with first. There is still a lot more to find out, especially with the intake, shooter, and amp systems because those components are so integrated. It was an interesting experience, requiring more thinking and design than command-based programming.

[commandbased]: ./commandBased.png
[statemachine]: ./stateMachine.png
