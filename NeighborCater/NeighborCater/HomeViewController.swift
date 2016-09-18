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
import STZPopupView

class KitchenTableViewCell: UITableViewCell {
    
    @IBOutlet weak var kitchenName: UILabel!
    @IBOutlet weak var foodName: UILabel!
    @IBOutlet weak var kitchenAddress: UILabel!
    @IBOutlet weak var price: UILabel!
    @IBOutlet weak var distance: UILabel!
    
}

class HomeViewController: UIViewController, MKMapViewDelegate, CLLocationManagerDelegate, UITableViewDataSource, UITableViewDelegate{

    @IBOutlet weak var mapView: MKMapView!
    
    let locationManager = CLLocationManager()
    
    var userLocation = CLLocationCoordinate2D()
    
    var Distances = [Double]()
    
    @IBOutlet weak var tableView: UITableView!
    
    var Kitchens = [Kitchen]()
    
    var Annotations = [Kitchen]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        var nib = UINib(nibName: "KitchenTableViewCell", bundle: nil)
        
        tableView.registerNib(nib, forCellReuseIdentifier: "kitchen")
        
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
        let region = MKCoordinateRegion(center: center, span: MKCoordinateSpan(latitudeDelta: 0.03, longitudeDelta: 0.03))
        
        self.mapView.setRegion(region, animated: true)
        
        self.mapView.showsUserLocation = true
        
        var circle = MKCircle(centerCoordinate: center, radius: 1600)
        
        mapView.addOverlay(circle)
        
        let ref = FIRDatabase.database().reference()
        let pathRef = ref.child("kitchens")
        
        let x = pathRef.observeEventType(FIRDataEventType.Value, withBlock: { (snapshot) in
            
            for item in snapshot.children.allObjects as! [FIRDataSnapshot]{
                let kitchen = Kitchen(snapshot: item);
                kitchen.addDistance(CLLocation(latitude: kitchen.latitude, longitude: kitchen.longitude).distanceFromLocation(CLLocation(latitude: location!.coordinate.latitude, longitude: location!.coordinate.longitude))/1609)
                
                print(kitchen.distance)
                if (kitchen.distance <= 1){
                    self.Kitchens.append(kitchen);
                    self.Annotations.append(kitchen)
                }
            }
                self.tableView.reloadData()
                self.addAnnotation()
        })
        
        
        self.locationManager.stopUpdatingLocation()
    }
    
    func addAnnotation() {
        for i in self.Annotations {
            let annot = MKPointAnnotation()
            annot.coordinate = CLLocationCoordinate2DMake(i.latitude, i.longitude)
            print(annot.coordinate)
            annot.title = i.kitchenName
            dispatch_async(dispatch_get_main_queue()) {
                self.mapView.addAnnotation(annot)
            }
            
            print("Annotation added!")
            print(self.mapView.annotations.count)
        }
        
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.Kitchens.count
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("kitchen") as! KitchenTableViewCell
        
        let kitch = Kitchens[indexPath.row]
        cell.kitchenAddress.text = kitch.kitchenAddress
        cell.foodName.text = kitch.foodName
        cell.kitchenName.text = kitch.kitchenName
        cell.price.text = "$" + kitch.price
        cell.distance.text = String(format: "%.2f",kitch.distance) + " mi"
        return cell
        
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
    
        var popup = NSBundle.mainBundle().loadNibNamed("BuyFoodView", owner: nil, options: nil)[0] as? BuyFoodView
        popup?.kitchenSelected = Kitchens[indexPath.row]
        popup?.setNeedsLayout()
        popup?.layoutIfNeeded()
        let popupView = popup
        popupView!.frame = CGRectMake(50,50,self.view.frame.size.width - 50, self.view.frame.size.height-200)
        let popupConfig = STZPopupViewConfig()
        popupConfig.cornerRadius = 10
        popup?.populateData()
        presentPopupView(popupView!, config: popupConfig)
        
    }
    
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError) {
        print ("Errors: " + error.localizedDescription)
    }
    
    func mapView(mapView: MKMapView, rendererForOverlay overlay: MKOverlay) -> MKOverlayRenderer! {
        let circleView = MKCircleRenderer(overlay: overlay)
        circleView.strokeColor = UIColor.blackColor()
        circleView.lineWidth = 2.0
        return circleView;
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

extension UIView {
    class func loadFromNibNamed(nibNamed: String, bundle : NSBundle? = nil) -> UIView? {
        return UINib(
            nibName: nibNamed,
            bundle: bundle
            ).instantiateWithOwner(nil, options: nil)[0] as? UIView
    }
}
