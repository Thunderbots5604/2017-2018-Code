package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BlueAuto", group="Autonomous")
public class BlueAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        double blockLeftInit = .75;
        double blockRightInit = .25;
        boolean complete = false;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        Servo lever = null;
        Servo hit = null;

        Servo flipblock = null;
        Servo blockright = null;
        Servo blockleft = null;

        CRServo grabber = null;

        OpticalDistanceSensor ods = null;
        ColorSensor color = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        ods = hardwareMap.opticalDistanceSensor.get("ods");
        color = hardwareMap.colorSensor.get("color");

        lever = hardwareMap.servo.get("lever");
        hit = hardwareMap.servo.get("hit");
        flipblock = hardwareMap.servo.get("flip_block");
        blockright = hardwareMap.servo.get("block_right");
        blockleft = hardwareMap.servo.get("block_left");
        grabber = hardwareMap.crservo.get("grabber");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);

        ods.enableLed(true);
        color.enableLed(true);

        waitForStart();
        runtime.reset();

        while(opModeIsActive() && !complete) {
            grabber.setPower(0);
            flipblock.setPosition(0);
            blockright.setPosition(.35);
            blockleft.setPosition(.55);

            hit.setPosition(.6);
            sleep(1000);
            lever.setPosition(.775);
            while(color.red() == 0 || color.blue() == 0) {
                hit.setPosition(hit.getPosition() + .01);
                sleep(250);
            }
            if((color.blue() + 1) < (color.red())) {

                telemetry.addLine("Is red");
                telemetry.update();
                sleep(1000);
                hit.setPosition(1);
            }
            else {
                telemetry.addLine("Is not Red");
                telemetry.update();
                sleep(1000);
                hit.setPosition(0);
            }
            sleep(1000);
            complete = true;
        }
        telemetry.addLine("Done ;)");
        telemetry.update();

    }
}
