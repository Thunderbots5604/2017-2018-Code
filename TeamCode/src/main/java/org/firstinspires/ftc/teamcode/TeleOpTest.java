package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math;

@TeleOp(name="TeleOp", group="TeleOp")
public class TeleOpTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime flipTime = new ElapsedTime();
    private ElapsedTime blockTime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        double blockLeftInit = .75;
        double blockRightInit = .25;

        boolean flip = false;
        boolean block = false;

        boolean hunter = false;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        DcMotor relicarm = null;
        DcMotor blockarm = null;

        Servo lever = null;
        Servo hit = null;

        Servo flipblock = null;
        Servo blockright = null;
        Servo blockleft = null;

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

        ods.enableLed(true);
        /*color.enableLed(true);*/

        waitForStart();
        runtime.reset();

        flipblock.setPosition(0);
        blockright.setPosition(blockRightInit);
        blockleft.setPosition(blockLeftInit);
        hit.setPosition(.6);
        lever.setPosition(.25);

        while (opModeIsActive()) {
  /*          boolean isFlat = gyro.getRotationFraction() < .2;*/
            boolean blockIn = ods.getLightDetected() > .05;


            //Stuff to display for Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
/*            telemetry.addData("Ultrasonic Sensor: ", ultra.getUltrasonicLevel());*/
            telemetry.addData("Red", color.red());
            telemetry.addData("Blue", color.blue());
/*            telemetry.addData("Gyro Rotation: ", gyro.getRotationFraction());
            telemetry.addData("Is level: ", isFlat);*/
            telemetry.addData("Touched: ", touch.isPressed());
/*            telemetry.addData("Hunter Mode: ", hunter);*/
            telemetry.addData("Left Front", "Ticks: " + leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Back", "Ticks: " + leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Front", "Ticks: " + rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back", "Ticks: " + rightMotorBack.getCurrentPosition());
            telemetry.addData("ODS", "Light: " + ods.getLightDetected());
            telemetry.addData("Block In: ", blockIn);
            telemetry.update();

            //Forward and backward moving method
            if(gamepad1.left_stick_y != 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
            }
            else if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(-gamepad1.left_stick_x);
                rightMotorFront.setPower(-gamepad1.left_stick_x);

                leftMotorBack.setPower(gamepad1.left_stick_x);
                rightMotorBack.setPower(gamepad1.left_stick_x);
            }
            else if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
            }
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if (gamepad1.left_stick_y !=0  && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);
            }
            else if(gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            }
            else if(gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }

            if(gamepad1.dpad_up || gamepad2.dpad_up) {
                blockarm.setPower(1);
            }
            else if ((gamepad1.dpad_down || gamepad2.dpad_down) && (touch.isPressed() == false)) {
                blockarm.setPower(-1);
            }
            else {
                blockarm.setPower(0);
            }

            if(gamepad2.a && flip == false && flipTime.seconds() > .5) {
                flipblock.setPosition(1);
                flipTime.reset();
                flip = true;
            }
            else if (gamepad2.a && flip == true && flipTime.seconds() > .5) {
                flipblock.setPosition(0);
                flipTime.reset();
                flip = false;
            }

            if (gamepad2.b && block == false && blockTime.seconds() > .5 /*&& hunter == false*/) {
                blockleft.setPosition(.95);
                blockright.setPosition(.1);
                block = true;
                blockTime.reset();
            }
            else if(gamepad2.b && block == true && blockTime.seconds() > .5/* && hunter == false*/) {
                blockleft.setPosition(.65);
                blockright.setPosition(.45);
                block = false;
                blockTime.reset();
            }

/*            if(gamepad2.right_bumper && hunter == false) {
                hunter = true;
            }
            else if (gamepad2.right_bumper && hunter == true) {
                hunter = false;
            }*/

            if(/*hunter && */blockIn && !gamepad2.b) {
                blockleft.setPosition(.65);
                blockright.setPosition(.45);
            }
            else if (/*hunter && */blockIn && gamepad2.b && blockTime.seconds() > .5) {
                blockleft.setPosition(.95);
                blockright.setPosition(.1);
                block = true;
                blockTime.reset();
            }
/*            else if (hunter && blockIn == false && !gamepad2.b) {
                blockleft.setPosition(.95);
                blockright.setPosition(.1);
            }*/

            if (gamepad2.dpad_left) {
                relicarm.setPower(1);
            }
            else if (gamepad2.dpad_right) {
                relicarm.setPower(-1);
            }
            else {
                relicarm.setPower(0);
            }
        }
    }
}

