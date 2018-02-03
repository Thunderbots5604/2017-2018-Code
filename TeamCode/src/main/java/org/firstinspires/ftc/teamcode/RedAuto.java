package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="RedAuto", group="Autonomous")
public class RedAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        double blockLeftInit = .75;
        double blockRightInit = .25;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        DcMotor relicarm = null;
        DcMotor blockarm = null;

        Servo lever = null;
        Servo hit = null;

        Servo blockright = null;
        Servo blockleft = null;
        Servo flipblock = null;

        OpticalDistanceSensor ods = null;
        TouchSensor touch = null;
        ColorSensor color = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        blockarm = hardwareMap.dcMotor.get("block_arm");
        relicarm = hardwareMap.dcMotor.get("relic_arm");

        ods = hardwareMap.opticalDistanceSensor.get("ods");
/*        gyro = hardwareMap.gyroScope.get("gyro");*/
        touch = hardwareMap.touchSensor.get("touch");
/*        ultra = hardwareMap.ultrasonicSensor.get("ultra");*/
        color = hardwareMap.colorSensor.get("color");

        lever = hardwareMap.servo.get("lever");
        hit = hardwareMap.servo.get("hit");
        flipblock = hardwareMap.servo.get("flip_block");
        blockright = hardwareMap.servo.get("block_right");
        blockleft = hardwareMap.servo.get("block_left");


        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);
        blockarm.setDirection(DcMotor.Direction.REVERSE);

        flipblock.setPosition(0);
        blockright.setPosition(blockRightInit);
        blockleft.setPosition(blockLeftInit);

        //.65 lever (higher equals down) .6 hit (higher equals more to the color sensor)

        lever.setPosition(.75);
        hit.setPosition(.6);
        waitForStart();
        runtime.reset();
        lever.setPosition(.75);
        hit.setPosition(.6);

        sleep(1000);
        while(color.red() == 0 || color.blue() == 0) {
            hit.setPosition(hit.getPosition() + .01);
            sleep(250);
        }

        if(color.blue() + 1 < (color.red())) {

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

        hit.setPosition(.6);
        lever.setPosition(.3);

        sleep(1000);
/*

        rightMotorFront.setPower(.25);
        rightMotorBack.setPower(.25);
        leftMotorFront.setPower(-.25);
        leftMotorBack.setPower(-.25);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.5)) {
            telemetry.addLine("Moving forward");
            telemetry.update();
        }
        telemetry.addLine("Done ;)");
        telemetry.update();
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);

        rightMotorFront.setPower(-.25);
        rightMotorBack.setPower(-.25);
        leftMotorFront.setPower(.25);
        leftMotorBack.setPower(.25);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addLine("Spin Out");
            telemetry.update();
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .4)) {
            telemetry.addLine("Just keep spinning");
            telemetry.update();
        }
*/

        telemetry.addLine("Done ;)");
        telemetry.update();

    }
}
