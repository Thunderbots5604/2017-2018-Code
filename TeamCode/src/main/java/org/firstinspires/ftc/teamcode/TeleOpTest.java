package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
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
        boolean quarterSpeed = false;
        double quarterMultiplier = .25;

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

        CRServo grabber = null;

        OpticalDistanceSensor ods = null;
        TouchSensor touch = null;
        TouchSensor relictouch = null;
        ColorSensor color = null;
        GyroSensor gyroSensor = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        blockarm = hardwareMap.dcMotor.get("block_arm");
        relicarm = hardwareMap.dcMotor.get("relic_arm");

        ods = hardwareMap.opticalDistanceSensor.get("ods");
        touch = hardwareMap.touchSensor.get("touch");
        relictouch = hardwareMap.touchSensor.get("relic_touch");
        color = hardwareMap.colorSensor.get("color");
        grabber = hardwareMap.crservo.get("grabber");

        lever = hardwareMap.servo.get("lever");
        hit = hardwareMap.servo.get("hit");
        flipblock = hardwareMap.servo.get("flip_block");
        blockright = hardwareMap.servo.get("block_right");
        blockleft = hardwareMap.servo.get("block_left");

        gyroSensor = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro_sensor");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);
        blockarm.setDirection(DcMotor.Direction.REVERSE);

        ods.enableLed(true);
        color.enableLed(true);
        gyroSensor.calibrate();

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
            telemetry.addData("Red", color.red());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("Lever", lever.getPosition());
            telemetry.addData("Hit", hit.getPosition());
            telemetry.addData("Left Front", "Ticks: " + leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Back", "Ticks: " + leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Front", "Ticks: " + rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back", "Ticks: " + rightMotorBack.getCurrentPosition());
            telemetry.addData("ODS", "Light: " + ods.getLightDetected());
            telemetry.addData("Block In: ", blockIn);
            telemetry.addData("Quarter Speed:", quarterSpeed);
            telemetry.update();

            if(gamepad1.left_trigger != 0) {
                quarterSpeed = true;
            }
            else {
                quarterSpeed = false;
            }

            //Forward and backward moving method
            if (gamepad1.left_stick_y != 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
            } else if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x != 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                leftMotorFront.setPower(-gamepad1.left_stick_x);
                rightMotorFront.setPower(-gamepad1.left_stick_x);

                leftMotorBack.setPower(gamepad1.left_stick_x);
                rightMotorBack.setPower(gamepad1.left_stick_x);
            } else if (gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
            } else if (gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x > 0 && !quarterSpeed) {
                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            } else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && !quarterSpeed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);
            } else if (gamepad1.right_stick_x > 0 && !quarterSpeed) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            } else if (gamepad1.right_stick_x < 0 && !quarterSpeed) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            }

            //Forward and backward moving method
            else if (gamepad1.left_stick_y != 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_y);
                rightMotorBack.setPower(quarterMultiplier * -gamepad1.left_stick_y);

                leftMotorFront.setPower(quarterMultiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * (-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x)));
                rightMotorBack.setPower(quarterMultiplier * -gamepad1.left_stick_y);

                leftMotorFront.setPower(quarterMultiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
            } else if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x != 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                leftMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_x);
                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_x);

                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_x);
                rightMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_x);
            } else if (gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_y);
                rightMotorBack.setPower(quarterMultiplier * (-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x)));

                leftMotorFront.setPower(quarterMultiplier * (gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x)));
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * (-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x)));
                rightMotorBack.setPower(quarterMultiplier * -gamepad1.left_stick_y);

                leftMotorFront.setPower(quarterMultiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(quarterMultiplier * (gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x)));
            } else if (gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_y);
                rightMotorBack.setPower(quarterMultiplier * (-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x)));

                leftMotorFront.setPower(quarterMultiplier * (gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x)));
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x > 0 && quarterSpeed) {
                leftMotorFront.setPower(quarterMultiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            } else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && quarterSpeed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_y);
                rightMotorBack.setPower(quarterMultiplier * -gamepad1.left_stick_y);
            } else if (gamepad1.right_stick_x > 0 && quarterSpeed) {
                leftMotorFront.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
                leftMotorBack.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));

                rightMotorFront.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
                rightMotorBack.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
            } else if (gamepad1.right_stick_x < 0 && quarterSpeed) {
                leftMotorFront.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
                leftMotorBack.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));

                rightMotorFront.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
                rightMotorBack.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }

            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                blockarm.setPower(1);
            } else if ((gamepad1.dpad_down || gamepad2.dpad_down) && (touch.isPressed() == false)) {
                blockarm.setPower(-1);
            } else {
                blockarm.setPower(0);
            }

            if (gamepad2.a && flip == false && flipTime.seconds() > .5) {
                flipblock.setPosition(1);
                flipTime.reset();
                flip = true;
            } else if (gamepad2.a && flip == true && flipTime.seconds() > .5) {
                flipblock.setPosition(0);
                flipTime.reset();
                flip = false;
            }
            if (gamepad2.right_bumper) {
                grabber.setPower(1.0);
            } else if (gamepad2.left_bumper) {
                grabber.setPower(-1.0);
            } else {
                grabber.setPower(0);
            }

            if (gamepad2.b && block == false && blockTime.seconds() > .5) {
                blockleft.setPosition(.95);
                blockright.setPosition(.1);
                block = true;
                blockTime.reset();
            } else if (gamepad2.b && block == true && blockTime.seconds() > .5/* && hunter == false*/) {
                blockleft.setPosition(.55);
                blockright.setPosition(.35);
                block = false;
                blockTime.reset();
            }

            if (gamepad2.dpad_left ) {
                relicarm.setPower(-1);
            } else if (gamepad2.dpad_right && (relictouch.isPressed() == false)) {
                relicarm.setPower(1);
            } else {
                relicarm.setPower(0);
            }

            if (gamepad2.left_stick_y != 0 && lever.getPosition() >= 0 && lever.getPosition() <= 1) {
                lever.setPosition(lever.getPosition() + (.0075 * gamepad2.left_stick_y));
            }
            if (gamepad2.right_stick_x != 0 && hit.getPosition() >= 0 && hit.getPosition() <= 1) {
                hit.setPosition(hit.getPosition() + (.01 * -gamepad2.right_stick_x));
            }
        }
    }
}

