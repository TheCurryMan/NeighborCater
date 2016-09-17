//
//  HomeViewController.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation
import Firebase
import FirebaseDatabase

class HomeViewController: UIViewController, MKMapViewDelegate, CLLocationManagerDelegate {

    @IBOutlet weak var mapView: MKMapView!
    
    let locationManager = CLLocationManager()
    
    var userLocation = CLLocationCoordinate2D()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.locationManager.delegate = self
        self.locationManager.desiredAccuracy = kCLLocationAccuracyBest
        self.locationManager.requestWhenInUseAuthorization()
        self.locationManager.startUpdatingLocation()
        

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let location = locations.last
        
        let center = CLLocationCoordinate2DMake(location!.coordinate.latitude, location!.coordinate.longitude)
        userLocation = center
        let region = MKCoordinateRegion(center: center, span: MKCoordinateSpan(latitudeDelta: 0.025, longitudeDelta: 0.025))
        
        self.mapView.setRegion(region, animated: true)
        
        self.mapView.showsUserLocation = true
        
        var circle = MKCircle(centerCoordinate: center, radius: 1600)
        
        mapView.addOverlay(circle)
        
        let ref = FIRDatabase.database().reference()
        let pathRef = ref.child("kitchens")
        
        var refHandle = pathRef.observeEventType(FIRDataEventType.Value, withBlock: { (snapshot) in
            let postDict = snapshot.value as! [String : AnyObject]
            for i in postDict {
                let lat = (i.1["latitude"] as! Double)
                let long = (i.1["longitude"] as! Double)
                if (CLLocation(latitude: lat, longitude: long).distanceFromLocation(CLLocation(latitude: location!.coordinate.latitude, longitude: location!.coordinate.longitude)))/1609 <= 1{
                    var annot = MKPointAnnotation()
                    annot.coordinate = CLLocationCoordinate2D(latitude: lat, longitude: long)
                    print(annot.coordinate)
                    annot.title = (i.1["kitchenName"] as! String)
                    self.mapView.addAnnotation(annot)
                    print("Annotation added!")
                }
            }
            // ...
        })
        
        
        self.locationManager.stopUpdatingLocation()
    }
    
    func mapView(mapView: MKMapView, viewForAnnotation annotation: MKAnnotation) -> MKAnnotationView? {
        // Don't want to show a custom image if the annotation is the user's location.
        guard !annotation.isKindOfClass(MKUserLocation) else {
            return nil
        }
        
        let annotationIdentifier = "AnnotationIdentifier"
        
        var annotationView: MKAnnotationView?
        if let dequeuedAnnotationView = mapView.dequeueReusableAnnotationViewWithIdentifier(annotationIdentifier) {
            annotationView = dequeuedAnnotationView
            annotationView?.annotation = annotation
        }
        else {
            let av = MKAnnotationView(annotation: annotation, reuseIdentifier: annotationIdentifier)
            //av.rightCalloutAccessoryView = UIButton(type: .DetailDisclosure)
            annotationView = av
        }
        
        if let annotationView = annotationView {
            // Configure your annotation view here
            annotationView.canShowCallout = true
            annotationView.image = UIImage(named: "mappin")
        }
        
        return annotationView
    }
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError) {
        print ("Errors: " + error.localizedDescription)
    }
    
    func mapView(mapView: MKMapView, rendererForOverlay overlay: MKOverlay) -> MKOverlayRenderer! {
        let circleView = MKCircleRenderer(overlay: overlay)
        circleView.strokeColor = UIColor.blackColor()
        circleView.lineWidth = 2.0
        return circleView;
        /*
        MKCircleRenderer *circleView = [[MKCircleRenderer alloc] initWithOverlay:overlay];
        circleView.strokeColor = [UIColor redColor];
        circleView.fillColor = [[UIColor redColor] colorWithAlphaComponent:0.4];
        return circleView; */
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
