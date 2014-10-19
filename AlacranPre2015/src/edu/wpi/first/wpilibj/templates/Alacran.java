package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Watchdog;
        
public class Alacran extends IterativeRobot {
    
    Joystick leftJoy, rightJoy;
    Joystick xBox1;
    
    Jaguar left, right;
    RobotDrive am14u;
    
    Compressor compressor;
    Solenoid catapult, hook, raiseClaws, lowerClaws, openClaws;
    
    public Alacran() {        
        //Motor Controllers and Drive Train
        left = new Jaguar(3);
        right = new Jaguar(2);
        am14u = new RobotDrive(left, right);
        
        //Joysticks
        leftJoy = new Joystick(1);
        rightJoy = new Joystick(2);
        xBox1 = new Joystick(3);
        
        //Compressor
        compressor = new Compressor(1, 2);
        
        //Solenoids
        catapult = new Solenoid(1);
        hook = new Solenoid(2);
        raiseClaws = new Solenoid(3);
        lowerClaws = new Solenoid(4);
        openClaws = new Solenoid(5);
    }
    
    public void robotInit() {
        //Invert motors for mechanical reasons
        //am14u.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        //am14u.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
    }
    
    public void teleopInit(){
        
    }

    public void teleopPeriodic() {        
        //Start compressor+pressure switch
        compressor.start();
        
        while(isOperatorControl() && isEnabled()){            
            //Feed watchdog for safety
            Watchdog.getInstance().feed();
            
            //Drive chassis
            //am14u.arcadeDrive(leftJoy);
            am14u.arcadeDrive(leftJoy.getAxis(Joystick.AxisType.kY), -leftJoy.getAxis(Joystick.AxisType.kX));
            
            
            //Control claws - open/close
            if(xBox1.getRawButton(3) && !xBox1.getRawButton(2)){
                openClaws.set(true);
            }
            else if(!xBox1.getRawButton(3) && xBox1.getRawButton(2)){
                openClaws.set(false);
            }
            
            //Control claws - raise/lower
            if(xBox1.getRawButton(1) && !xBox1.getRawButton(4)){
                lowerClaws.set(false);
                raiseClaws.set(true);
            }
            else if(!xBox1.getRawButton(1) && xBox1.getRawButton(4)){
                raiseClaws.set(false);
                lowerClaws.set(true);
            }
            
            //Catapult - main pistons
            if(xBox1.getRawButton(7) && !xBox1.getRawButton(8)){
                catapult.set(true);
            }
            else if(!xBox1.getRawButton(7) && xBox1.getRawButton(8)){
                catapult.set(false);
            }
            
            //Catapult - hook
            if(xBox1.getRawButton(5) && !xBox1.getRawButton(6)){
                hook.set(true);
            }
            else if(!xBox1.getRawButton(5) && xBox1.getRawButton(6)){
                hook.set(false);
            }
        }        
    }
}