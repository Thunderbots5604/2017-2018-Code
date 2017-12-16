package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="GyroAutonomous", group="Autonomous")
public class AutoGyro extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        DcMotor arm = null;

        CRServo spinleft = null;
        CRServo spinright = null;

        OpticalDistanceSensor ods = null;
        GyroSensor gyro = null;
        TouchSensor touch = null;
        UltrasonicSensor ultra = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        arm = hardwareMap.dcMotor.get("arm");

        spinleft = hardwareMap.crservo.get("spinleft");
        spinright = hardwareMap.crservo.get("spinright");

        ods = hardwareMap.opticalDistanceSensor.get("ods");
        gyro = hardwareMap.gyroSensor.get("gyro");
        touch = hardwareMap.touchSensor.get("touch");
        ultra = hardwareMap.ultrasonicSensor.get("ultra");

        runtime.reset();
        gyro.calibrate();

        while(gyro.isCalibrating() || runtime.seconds() < 35) {
            telemetry.addLine("Gyro is calibrating");
            telemetry.update();
        }

        telemetry.addLine("Gyro has calibrated (maybe?)");

        waitForStart();
        runtime.reset();

        rightMotorFront.setPower(.5);
        rightMotorBack.setPower(.5);
        leftMotorFront.setPower(-.5);
        leftMotorBack.setPower(-.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addLine("Moving forward");
            telemetry.update();
        }
        telemetry.addLine("Done ;)");
        telemetry.update();
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
}
